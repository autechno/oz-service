// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/proxy/Clones.sol";


interface IChildContract {
    function initialize(address _owner) external;
}

interface IERC20 {
    function transferFrom(address sender, address recipient, uint256 amount) external returns (bool);
}

contract ChildContract {
    address public owner;

    // 初始化函数
    function initialize(address _owner) external {
        require(owner == address(0), "Already initialized");
        owner = _owner;
    }

    // 接收 ERC20 代币
    function receiveERC20(address token, uint256 amount) external {
        require(IERC20(token).transferFrom(msg.sender, address(this), amount), "Transfer failed");
    }

    // 其他代币相关操作
    function withdrawERC20(address token, uint256 amount) external {
        require(msg.sender == owner, "Only owner can withdraw");
        require(IERC20(token).transferFrom(address(this), msg.sender, amount), "Withdrawal failed");
    }
}


contract ProxyFactory {
    address public childContractLogic;  // 子合约的逻辑合约地址

    // 用于存储所有创建的代理合约地址
    address[] public proxies;

    // 设置子合约的逻辑地址
    constructor() {
        childContractLogic = address(new ChildContract());
    }

    // 创建新的代理合约
    function createProxy() external returns (address) {
        address proxy = Clones.clone(childContractLogic);
        // 初始化子合约
        ChildContract(proxy).initialize(address(this));
        proxies.push(proxy);
        return address(proxy);
    }

    // 获取所有创建的代理合约地址
    function getProxies() external view returns (address[] memory) {
        return proxies;
    }
}
