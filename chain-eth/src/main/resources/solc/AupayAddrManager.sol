// SPDX-License-Identifier: MIT

pragma solidity ^0.8.20;

import {Common} from "./library/Common.sol";
import {IERC20} from "./library/IERC20.sol";
import {Ownable} from "./library/Ownable.sol";

interface IUSDT {
    function balanceOf(address account) external view returns (uint256);
    function transfer(address recipient, uint256 amount) external;
}

struct WithdrawMeta {
    address payable toAddr;
    uint amount;
}
struct CollectMeta {
    address token;
    uint limit;
}
struct TokenBalance {
    address token;
    uint balance;
}

// 子合约
contract WalletContract {

    address public owner;
    bool public paused = false;

    // 初始化时设置主合约为 owner
    constructor(address _owner) {
        owner = _owner;
    }

    // 接收ETH的回调函数
    receive() external payable {}
    
    modifier onlyOwner() {
        require(msg.sender == owner, "not owner!");
        _;
    }

    modifier whenNotPaused() {
        require(!paused, "Must be used without pausing");
        _;
    }
    // 子合约提取ETH
    function withdraw(address payable recipient, uint amount) external onlyOwner whenNotPaused {
        require(address(this).balance >= amount, "Insufficient balance");
        recipient.transfer(amount);
    }

    function withdrawToken(address token, address to, uint amount) external onlyOwner whenNotPaused {
        if (token == Common.USDT_ADDR) {
            IUSDT(token).transfer(to,amount);
        } else {
            IERC20(token).transfer(to, amount);
        }
    }

    // 停用合约，停用前转完代币余额
    function destroy() external onlyOwner {
        paused = true;
    }
    function reEnable() external onlyOwner {
        paused = false;
    }
}

// 主合约
contract WalletManagerContract is Ownable {
    // 记录已部署的子合约
    address payable[] private userWallets;//用户钱包
    address payable private collectWallet;//归集钱包
    address payable private withDrawWallet;//提币钱包

    address[] private tokenContracts;

    function generateWallet() internal returns (address payable) {
        WalletContract subContract = new WalletContract(address(this));
        address _addr = address(subContract);
        return payable(_addr);
    }

    // 批量生成新的用户合约
    function generateUserWalletBatch(uint count) public onlyOwner returns (address payable[] memory) {
        address payable[] memory userAddr = new address payable[] (count);
        for (uint i = 0; i < count; i ++) {
            address payable _addr = generateWallet();
            userWallets.push(_addr);
            userAddr[i] = _addr;
        }
        return userAddr;
    }
    // 生成新的用户合约
    function generateUserWallet() public onlyOwner returns (address payable) {
        address payable _addr = generateWallet();
        userWallets.push(_addr);
        return _addr;
    }
    function reEnableUserWallet(address payable[] memory _addrArr) public onlyOwner {
        for (uint i = 0; i < _addrArr.length; i ++) {
            WalletContract(_addrArr[i]).reEnable();
            userWallets.push(_addrArr[i]);
        }
    }


    // 生成新的归集合约
    function reGenerateCollectWallet() public onlyOwner returns (address) {
        address payable _wallet = generateWallet();
        if (collectWallet != address(0)) {
            CollectMeta[] memory collectMatas = limit0();
            transferTo(collectWallet, _wallet, collectMatas);
            destroyWallet(collectWallet);
        }
        collectWallet = _wallet;
        return collectWallet;
    }

    // 生成新的提款合约
    function reGenerateWithdrawWallet() public onlyOwner returns (address) {
        address payable _wallet = generateWallet();
        if (withDrawWallet != address(0)) {
            CollectMeta[] memory collectMatas = limit0();
            transferTo(withDrawWallet, _wallet, collectMatas);
            destroyWallet(withDrawWallet);
        }
        withDrawWallet = _wallet;
        return withDrawWallet;
    }

    function balanceOf(address payable _wallet) external onlyOwner view returns (TokenBalance[] memory) {
        TokenBalance[] memory _balanceArr = new TokenBalance[](tokenContracts.length);
        for (uint i = 0; i < tokenContracts.length; i ++) {
            address _token = tokenContracts[i];
            uint _balance = balanceOfToken(_wallet, _token);
            _balanceArr[i] = TokenBalance(_token, _balance);
        }
        return _balanceArr;
    }
    function balanceOfToken(address payable _wallet,address token) public onlyOwner view returns (uint) {
        if (token == Common.ETH_SIGN_ADDR) {
            return _wallet.balance;
        } else {
            return IERC20(token).balanceOf(_wallet);
        }
    }

    //支付提币
    function withdrawETH(address payable toAddr, uint amount) public onlyOwner {
        WalletContract(withDrawWallet).withdraw(toAddr, amount);
    }
    function withdrawETHBatch(WithdrawMeta[] memory wdArr) public onlyOwner {
        uint _amountAll;
        for (uint i = 0; i < wdArr.length; i++) {
            _amountAll += wdArr[i].amount;
        }
        require(withDrawWallet.balance >= _amountAll, "Insufficient balance");
        WalletContract wallet = WalletContract(withDrawWallet);
        for (uint i = 0; i < wdArr.length; i++) {
            wallet.withdraw(wdArr[i].toAddr, wdArr[i].amount);
        }
    }
    function withdrawToken(address token, address toAddr, uint amount) public onlyOwner {
        WalletContract(withDrawWallet).withdrawToken(token, toAddr, amount);
    }
    function withdrawTokenBatch(address token, WithdrawMeta[] memory wdArr) public onlyOwner {
        uint _amountAll;
        for (uint i = 0; i < wdArr.length; i++) {
            _amountAll += wdArr[i].amount;
        }
        uint balance = IERC20(token).balanceOf(withDrawWallet);
        require( balance >= _amountAll, "Insufficient balance");
        WalletContract wallet = WalletContract(withDrawWallet);
        for (uint i = 0; i < wdArr.length; i++) {
            wallet.withdrawToken(token, wdArr[i].toAddr, wdArr[i].amount);
        }
    }

    //转储
    function collect2storage(address payable storageWallet, CollectMeta[] memory collectMatas) external onlyOwner {
        transferTo(collectWallet, storageWallet, collectMatas);
    }
    //补充提币钱包
    function collect2withdraw(CollectMeta[] memory collectMatas) external onlyOwner {
        transferTo(collectWallet, withDrawWallet, collectMatas);
    }

    //归集
    function collect(CollectMeta[] memory collectMatas) external onlyOwner {
        for (uint i = 0; i < userWallets.length; i++) {
            transferTo(userWallets[i], collectWallet, collectMatas);
        }
    }

    //回收全部用户地址
    function recycleUserWallets() external onlyOwner {
        CollectMeta[] memory collectMatas = limit0();
        while (userWallets.length > 0) {
            address payable _addr = userWallets[userWallets.length - 1];
            transferTo(_addr, collectWallet, collectMatas);
            destroyWallet(_addr);
            userWallets.pop();
        }
    }
    //回收单个用户地址
    function recycleUserWallet(address payable _addr) external onlyOwner {
        CollectMeta[] memory collectMatas = limit0();
        for (uint i = 0; i < userWallets.length; i++) {
            if(userWallets[i] == _addr) {
                userWallets[i] = userWallets[userWallets.length - 1];
                transferTo(_addr, collectWallet, collectMatas);
                destroyWallet(_addr);
                userWallets.pop();
                break;
            }
        }
    }
    function limit0() internal view returns (CollectMeta[] memory) {
        CollectMeta[] memory tokens = new CollectMeta[] (tokenContracts.length);
        for (uint i = 0; i < tokenContracts.length; i++) {
            tokens[i] = CollectMeta(tokenContracts[i], 0);
        }
        return tokens;
    }

    // 从指定子合约提取代币并转移到目标地址
    function transferTo(
        address payable fromWallet,
        address payable toWallet,
        CollectMeta[] memory collectMatas
    ) internal {
        WalletContract wallet = WalletContract(fromWallet);
        for (uint i = 0; i < collectMatas.length; i++) {
            if (collectMatas[i].token == Common.ETH_SIGN_ADDR) {
                uint balance = fromWallet.balance;
                if (balance > 0 && balance >= collectMatas[i].limit) {
                    wallet.withdraw(toWallet, balance);
                }
            } else {
                uint balance = IERC20(collectMatas[i].token).balanceOf(fromWallet);
                if (balance > 0 && balance >= collectMatas[i].limit) {
                    wallet.withdrawToken(collectMatas[i].token, toWallet, balance);
                }
            }
        }
    }

    // 销毁指定子合约
    function destroyWallet(address payable wallet) internal {
        WalletContract(wallet).destroy();
    }

    // 获取所有用户钱包地址
    function getUserWallets() external onlyOwner view returns (address payable[] memory) {
        return userWallets;
    }
    // 获取归集钱包地址
    function getCollectWallet() external onlyOwner view returns (address payable) {
        return collectWallet;
    }
    // 获取提币钱包地址
    function getWithdrawWallet() external onlyOwner view returns (address payable) {
        return withDrawWallet;
    }

    //代币合营管理
    function getTokenContracts() external onlyOwner view returns (address[] memory) {
        return tokenContracts;
    }
    function pushTokenContracts(address token) external onlyOwner {
        tokenContracts.push(token);
    }
    function popTokenContracts(address token) external onlyOwner {
        for (uint i = 0; i < tokenContracts.length; i ++) {
            if (tokenContracts[i] == token) {
                tokenContracts[i] = tokenContracts[tokenContracts.length - 1];
                tokenContracts.pop();
                break;
            }
        }
    }

    constructor () Ownable(msg.sender) {
        tokenContracts.push(0x19c0947b0E1169C00854CaF563E20D742C462eE6);//usdt
        tokenContracts.push(0xAb104736299169E3a252E32e49D6f30273832734);//ozc
        tokenContracts.push(0x544d4e0CC6C1cd0d319f3ceB2a8Dbba1B4dE9Dd2);//toto
        tokenContracts.push(address(0));//eth

        collectWallet = generateWallet();
        withDrawWallet = generateWallet();

        generateUserWalletBatch(10);
    }
}
