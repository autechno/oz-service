// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.20;

library Common {

    enum PoolId {
        Pass,
        OzGroupPool,
        OzSupporterPool,
        OzFoundationPool,
        StakePool,
        OzbetPool,
        OzbetVipPool
    }

    struct Permit {
        address owner;
        address spender;
        uint256 value;
        uint256 nonce;
        uint256 deadline;
    }

    address public constant USDT_ADDR = 0x19c0947b0E1169C00854CaF563E20D742C462eE6;
    address public constant ETH_SIGN_ADDR = address(0);

}
