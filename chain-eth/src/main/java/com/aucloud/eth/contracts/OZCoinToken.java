package com.aucloud.eth.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.12.1.
 */
@SuppressWarnings("rawtypes")
public class OZCoinToken extends Contract {
    public static final String BINARY = "6080604052600a805461ffff1916905534801561001a575f80fd5b50604051612092380380612092833981016040819052610039916101dd565b338061005e57604051631e4fbdf760e01b81525f600482015260240160405180910390fd5b61006781610173565b506001600160a01b039081165f90815260046020908152604091829020600190819055600380546001600160a01b031916959094169490941790925580518082018252600681526527ad21b7b4b760d11b9083015280518082018252838152603160f81b9083015280517f8b73c3c69bb8fe3d512ecc4cf759cc79239f7b179b0ffacaa9a75d522b39400f818401527fb8b416a9921d420ae19117d2b045e716054c477135ec3ea9f756764e905b8e20818301527fc89efdaa54c0f20c7adf612882df0950f5a951637e0307cdcb4c672f298b8bc660608201524660808201523060a0808301919091528251808303909101815260c0909101909152805191012060095542905561020e565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b80516001600160a01b03811681146101d8575f80fd5b919050565b5f80604083850312156101ee575f80fd5b6101f7836101c2565b9150610205602084016101c2565b90509250929050565b611e778061021b5f395ff3fe608060405234801561000f575f80fd5b5060043610610213575f3560e01c80637ecebe001161011f578063a9059cbb116100a9578063dd336c1211610079578063dd336c1214610499578063dd62ed3e146104c3578063e5d9b06e146104fb578063f2fde38b1461050e578063ff192bc814610521575f80fd5b8063a9059cbb14610458578063adb294441461046b578063b2dfec511461047e578063bb702c8e14610491575f80fd5b8063969e3756116100ef578063969e37561461041457806396dadfbc146104275780639ced0e9b14610434578063a1545ef51461043d578063a34d42b814610445575f80fd5b80637ecebe00146103ac5780638456cb59146103cb5780638da5cb5b146103d357806395d89b41146103ed575f80fd5b80633f4ba83a116101a05780636bdf1e21116101705780636bdf1e211461034e5780636e593a3b1461036157806370a0823114610369578063715018a6146103915780637dcc7fd514610399575f80fd5b80633f4ba83a1461030e57806340c10f191461031657806351e946d5146103295780635c975abb1461033c575f80fd5b80631ee08b69116101e65780631ee08b691461029c57806323b872dd146102af578063313ce567146102c2578063357cd979146102dc57806339dbcb63146102fb575f80fd5b806301e336671461021757806306fdde031461022c578063095ea7b31461026757806318160ddd1461028a575b5f80fd5b61022a6102253660046119b7565b610543565b005b6102516040518060400160405280600681526020016527ad21b7b4b760d11b81525081565b60405161025e91906119f1565b60405180910390f35b61027a610275366004611a26565b61060f565b604051901515815260200161025e565b6002545b60405190815260200161025e565b61027a6102aa366004611a26565b61069e565b61027a6102bd3660046119b7565b610790565b6102ca601281565b60405160ff909116815260200161025e565b61028e6102ea366004611a4e565b60046020525f908152604090205481565b61027a610309366004611a26565b610821565b61022a6108a5565b61027a610324366004611a26565b61095b565b61027a610337366004611a4e565b610998565b600a5461027a90610100900460ff1681565b61022a61035c3660046119b7565b610a23565b61027a610ba7565b61028e610377366004611a4e565b6001600160a01b03165f9081526005602052604090205490565b61022a610be4565b61022a6103a7366004611aeb565b610bf7565b61028e6103ba366004611a4e565b60086020525f908152604090205481565b61022a610d96565b5f546040516001600160a01b03909116815260200161025e565b610251604051806040016040528060088152602001674f5a43626574613160c01b81525081565b61022a6104223660046119b7565b610e21565b600a5461027a9060ff1681565b61028e60015481565b61028e61112c565b61027a610453366004611a4e565b611179565b61027a610466366004611a26565b6111b6565b61027a610479366004611a4e565b61123a565b61028e61048c366004611b77565b611283565b61027a6112c8565b61028e6104a7366004611bad565b600660209081525f928352604080842090915290825290205481565b61028e6104d1366004611bad565b6001600160a01b039182165f90815260066020908152604080832093909416825291909152205490565b61027a610509366004611a4e565b61132f565b61022a61051c366004611a4e565b61137b565b61027a61052f366004611a4e565b60076020525f908152604090205460ff1681565b6003546001600160a01b031633146105765760405162461bcd60e51b815260040161056d90611bde565b60405180910390fd5b6001600160a01b0383165f908152600460205260409020546001146105ad5760405162461bcd60e51b815260040161056d90611c01565b60405163a9059cbb60e01b81526001600160a01b0383811660048301526024820183905284169063a9059cbb906044015f604051808303815f87803b1580156105f4575f80fd5b505af1158015610606573d5f803e3d5ffd5b50505050505050565b5f604061061d816004611c3c565b36101561063c5760405162461bcd60e51b815260040161056d90611c4f565b600a5460ff161561065f5760405162461bcd60e51b815260040161056d90611c7e565b600a54610100900460ff16156106875760405162461bcd60e51b815260040161056d90611cab565b336106938186866113b8565b506001949350505050565b6003545f906001600160a01b031633146106ca5760405162461bcd60e51b815260040161056d90611bde565b6001600160a01b0383165f9081526007602052604090205460ff1661071e5760405162461bcd60e51b815260206004820152600a6024820152694e6f7420667265657a6560b01b604482015260640161056d565b6001600160a01b0383165f9081526005602052604090205482111561077a5760405162461bcd60e51b8152602060048201526012602482015271496e73756666696369656e742066756e647360701b604482015260640161056d565b6107848383611418565b50600190505b92915050565b5f606061079e816004611c3c565b3610156107bd5760405162461bcd60e51b815260040161056d90611c4f565b600a5460ff16156107e05760405162461bcd60e51b815260040161056d90611c7e565b600a54610100900460ff16156108085760405162461bcd60e51b815260040161056d90611cab565b336108158187878761142f565b50600195945050505050565b5f604061082f816004611c3c565b36101561084e5760405162461bcd60e51b815260040161056d90611c4f565b600a5460ff16156108715760405162461bcd60e51b815260040161056d90611c7e565b600a54610100900460ff16156108995760405162461bcd60e51b815260040161056d90611cab565b336106938186866114e9565b6003546001600160a01b031633146108cf5760405162461bcd60e51b815260040161056d90611bde565b600a54610100900460ff166109265760405162461bcd60e51b815260206004820152601860248201527f4d757374206265207573656420756e6465722070617573650000000000000000604482015260640161056d565b600a805461ff00191690556040517f7805862f689e2f13df9f062ff482ad3ad112aca9e0847911ed832e158c525b33905f90a1565b6003545f906001600160a01b031633146109875760405162461bcd60e51b815260040161056d90611bde565b6109918284611580565b9392505050565b6003545f906001600160a01b031633146109c45760405162461bcd60e51b815260040161056d90611bde565b6001600160a01b0382165f81815260076020908152604091829020805460ff1916600117905590519182527faf85b60d26151edd11443b704d424da6c43d0468f2235ebae3d1904dbc323049910160405180910390a15060015b919050565b6001600160a01b0382165f90815260046020526040902054600114610a5a5760405162461bcd60e51b815260040161056d90611c01565b5f3390505f836001600160a01b031663313ce5676040518163ffffffff1660e01b8152600401602060405180830381865afa158015610a9b573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610abf9190611ce2565b905082600a601260ff84161015610afd575f610adc846012611cfd565b90505f610aec8360ff8416611619565b9050610af88782611653565b935050505b601260ff84161115610b36575f610b15601285611cfd565b90505f610b258360ff8416611619565b9050610b31878261165f565b935050505b610b408486611418565b5060405163a9059cbb60e01b81526001600160a01b0388811660048301526024820184905287169063a9059cbb906044015f604051808303815f87803b158015610b88575f80fd5b505af1158015610b9a573d5f803e3d5ffd5b5050505050505050505050565b6003545f906001600160a01b03163314610bd35760405162461bcd60e51b815260040161056d90611bde565b50600a805460ff1916905560015b90565b610bec611693565b610bf55f6116bf565b565b4284608001511015610c355760405162461bcd60e51b8152602060048201526007602482015266115e1c1a5c995960ca1b604482015260640161056d565b83516001600160a01b03165f90815260086020526040902054606085015111610c905760405162461bcd60e51b815260206004820152600d60248201526c496e76616c6964204e6f6e636560981b604482015260640161056d565b5f610c9a8561170e565b604080515f8082526020820180845284905260ff88169282019290925260608101869052608081018590529192509060019060a0016020604051602081039080840390855afa158015610cef573d5f803e3d5ffd5b5050604051601f1901519150506001600160a01b03811615801590610d20575085516001600160a01b038281169116145b610d605760405162461bcd60e51b8152602060048201526011602482015270496e76616c6964205369676e617475726560781b604482015260640161056d565b610d76865f0151876020015188604001516113b8565b505092516001600160a01b03165f90815260086020526040812055505050565b6003546001600160a01b03163314610dc05760405162461bcd60e51b815260040161056d90611bde565b600a54610100900460ff1615610de85760405162461bcd60e51b815260040161056d90611cab565b600a805461ff0019166101001790556040517f6985a02210a168e66602d3235cb6db0e70f92b3ba4d376a33c0f3d9434bff625905f90a1565b6001600160a01b0382165f90815260046020526040902054600114610e585760405162461bcd60e51b815260040161056d90611c01565b604051636eb1769f60e11b815233600482018190523060248301819052909184905f906001600160a01b0383169063dd62ed3e90604401602060405180830381865afa158015610eaa573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610ece9190611d16565b905084811015610f195760405162461bcd60e51b8152602060048201526016602482015275496e73756666696369656e7420616c6c6f77616e636560501b604482015260640161056d565b6040516323b872dd60e01b81526001600160a01b0385811660048301528481166024830152604482018790528316906323b872dd906064015f604051808303815f87803b158015610f68575f80fd5b505af1925050508015610f79575060015b61104a57610f85611d2d565b806308c379a003610fbe5750610f99611d45565b80610fa45750610fc0565b8060405162461bcd60e51b815260040161056d91906119f1565b505b3d808015610fe9576040519150601f19603f3d011682016040523d82523d5f602084013e610fee565b606091505b5060405162461bcd60e51b815260206004820152602a60248201527f696572633230207472616e7366657246726f6d206661696c2e206c6f77206c656044820152693b32b61032b93937b91760b11b606482015260840161056d565b5f826001600160a01b031663313ce5676040518163ffffffff1660e01b8152600401602060405180830381865afa158015611087573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906110ab9190611ce2565b905085600a601260ff841610156110e9575f6110c8846012611cfd565b90505f6110d88360ff8416611619565b90506110e48a8261165f565b935050505b601260ff84161115611122575f611101601285611cfd565b90505f6111118360ff8416611619565b905061111d8a82611653565b935050505b610b9a828b611580565b6001545f90429082906111409083906117e9565b90505f6111506201518083611ddd565b90505f6111606201518084611df0565b111561099157611171600182611c3c565b949350505050565b6003545f906001600160a01b031633146111a55760405162461bcd60e51b815260040161056d90611bde565b6111ae826116bf565b506001919050565b5f60406111c4816004611c3c565b3610156111e35760405162461bcd60e51b815260040161056d90611c4f565b600a5460ff16156112065760405162461bcd60e51b815260040161056d90611c7e565b600a54610100900460ff161561122e5760405162461bcd60e51b815260040161056d90611cab565b33610693818686611804565b6003545f906001600160a01b031633146112665760405162461bcd60e51b815260040161056d90611bde565b506001600160a01b03165f90815260046020526040812055600190565b6040805160a0810182523381526001600160a01b038616602082015290810184905260608101839052608081018290525f906112be8161170e565b9695505050505050565b6003545f906001600160a01b031633146112f45760405162461bcd60e51b815260040161056d90611bde565b600a805460ff191660011790556040517feac63f06e39ba0c87efe71a11df4d2cd90994b49b3809b7f07e3a9d366626faf905f90a150600190565b6003545f906001600160a01b0316331461135b5760405162461bcd60e51b815260040161056d90611bde565b506001600160a01b03165f90815260046020526040902060019081905590565b611383611693565b6001600160a01b0381166113ac57604051631e4fbdf760e01b81525f600482015260240161056d565b6113b5816116bf565b50565b6001600160a01b038381165f8181526006602090815260408083209487168084529482529182902085905590518481527f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925910160405180910390a3505050565b5f80611425848285611804565b5060019392505050565b6001600160a01b038084165f90815260066020908152604080832093881683529290522054808211156114a45760405162461bcd60e51b815260206004820181905260248201527f496e73756666696369656e742072656d61696e696e6720616c6c6f77616e6365604482015260640161056d565b6114ae81836117e9565b6001600160a01b038086165f908152600660209081526040808320938a1683529290522081905590506114e2848484611804565b5050505050565b6001600160a01b038084165f9081526006602090815260408083209386168352929052205461151881836117e9565b6001600160a01b038581165f818152600660209081526040808320948916808452948252918290208590559051868152939450919290917fd5b4f0da4ef064198c4a3e1b30b61495b1c38a7b5f8334fcbb3e242f613a31c9910160405180910390a350505050565b6001600160a01b0381165f9081526005602052604081205481906115a49085611983565b6001600160a01b0384165f908152600560205260409020556002546115c99085611983565b6002556040518481526001600160a01b0380851691908316907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9060200160405180910390a35060019392505050565b5f60015b8215610991578260011660010361163b576116388482611e03565b90505b60019290921c9161164c8480611e03565b935061161d565b5f806111718385611ddd565b5f825f0361166e57505f61078a565b5f6116798385611e03565b9050826116868583611ddd565b1461099157610991611e1a565b5f546001600160a01b03163314610bf55760405163118cdaa760e01b815233600482015260240161056d565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b5f6009547f6e71edae12b1b97f4d1f60370fef10105fa2faae0126114a169c64845d6126c9835f0151846020015185604001518660600151876080015160405160200161178f969594939291909586526001600160a01b0394851660208701529290931660408501526060840152608083019190915260a082015260c00190565b604051602081830303815290604052805190602001206040516020016117cc92919061190160f01b81526002810192909252602282015260420190565b604051602081830303815290604052805190602001209050919050565b5f828211156117fa576117fa611e1a565b6109918284611e2e565b6001600160a01b0383165f9081526007602052604090205460ff16158061183257506001600160a01b038216155b61186c5760405162461bcd60e51b815260206004820152600b60248201526a2132b2b710333937bd32b760a91b604482015260640161056d565b6001600160a01b0383165f90815260056020526040902054818110156118c95760405162461bcd60e51b8152602060048201526012602482015271496e73756666696369656e742066756e647360701b604482015260640161056d565b6118d381836117e9565b6001600160a01b038086165f9081526005602052604080822093909355908516815220546119019083611983565b6001600160a01b038085165f8181526005602052604090819020939093559151908616907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef906119549086815260200190565b60405180910390a36001600160a01b0383165f0361197d5760025461197990836117e9565b6002555b50505050565b5f8061198f8385611c3c565b90508381101561099157610991611e1a565b80356001600160a01b0381168114610a1e575f80fd5b5f805f606084860312156119c9575f80fd5b6119d2846119a1565b92506119e0602085016119a1565b929592945050506040919091013590565b602081525f82518060208401528060208501604085015e5f604082850101526040601f19601f83011684010191505092915050565b5f8060408385031215611a37575f80fd5b611a40836119a1565b946020939093013593505050565b5f60208284031215611a5e575f80fd5b610991826119a1565b60a0810181811067ffffffffffffffff82111715611a9357634e487b7160e01b5f52604160045260245ffd5b60405250565b601f8201601f1916810167ffffffffffffffff81118282101715611acb57634e487b7160e01b5f52604160045260245ffd5b6040525050565b60ff811681146113b5575f80fd5b8035610a1e81611ad2565b5f805f80848603610100811215611b00575f80fd5b60a0811215611b0d575f80fd5b50604051611b1a81611a67565b611b23866119a1565b8152611b31602087016119a1565b60208201526040868101359082015260608087013590820152608080870135908201529350611b6260a08601611ae0565b939693955050505060c08201359160e0013590565b5f805f8060808587031215611b8a575f80fd5b611b93856119a1565b966020860135965060408601359560600135945092505050565b5f8060408385031215611bbe575f80fd5b611bc7836119a1565b9150611bd5602084016119a1565b90509250929050565b6020808252600990820152682337b93134b23232b760b91b604082015260600190565b6020808252600d908201526c111bdb89dd081cdd5c1c1bdc9d609a1b604082015260600190565b634e487b7160e01b5f52601160045260245ffd5b8082018082111561078a5761078a611c28565b602080825260159082015274496e76616c69642073686f7274206164647265737360581b604082015260600190565b60208082526013908201527212185d99481899595b88191a5cd8d85c991959606a1b604082015260600190565b6020808252601c908201527f4d757374206265207573656420776974686f75742070617573696e6700000000604082015260600190565b5f60208284031215611cf2575f80fd5b815161099181611ad2565b60ff828116828216039081111561078a5761078a611c28565b5f60208284031215611d26575f80fd5b5051919050565b5f60033d1115610be15760045f803e505f5160e01c90565b5f60443d1015611d525790565b6040513d600319016004823e80513d602482011167ffffffffffffffff82111715611d7c57505090565b808201805167ffffffffffffffff811115611d98575050505090565b3d8401600319018282016020011115611db2575050505090565b611dc160208285010185611a99565b509392505050565b634e487b7160e01b5f52601260045260245ffd5b5f82611deb57611deb611dc9565b500490565b5f82611dfe57611dfe611dc9565b500690565b808202811582820484141761078a5761078a611c28565b634e487b7160e01b5f52600160045260245ffd5b8181038181111561078a5761078a611c2856fea2646970667358221220966a5a05e833f7723f66868d3046e97d1a518fbd8bd3cf525880c6be78468fe464736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC__ALLOWANCE = "_allowance";

    public static final String FUNC_ALLOWSUPPORTEDADDRESS = "allowSupportedAddress";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURNFREEZEADDRESSCOIN = "burnFreezeAddressCoin";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DECREASEAPPROVE = "decreaseApprove";

    public static final String FUNC_DISCARD = "discard";

    public static final String FUNC_DISCARDED = "discarded";

    public static final String FUNC_EXCHANGE = "exchange";

    public static final String FUNC_FREEZEADDRESS = "freezeAddress";

    public static final String FUNC_GETDAYS = "getDays";

    public static final String FUNC_HASHPERMIT = "hashPermit";

    public static final String FUNC_INITIALTIME = "initialTime";

    public static final String FUNC_ISFREEZE = "isFreeze";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_NONCES = "nonces";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_PERMITAPPROVE = "permitApprove";

    public static final String FUNC_REMOVESUPPORTEDADDRESSALLOW = "removeSupportedAddressAllow";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REVERSEEXCHANGE = "reverseExchange";

    public static final String FUNC_ROLLBACKDISCARD = "rollbackDiscard";

    public static final String FUNC_SETCONTRACTOWNER = "setContractOwner";

    public static final String FUNC_SUPPORTEDCONTRACTADDRESS = "supportedContractAddress";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DECREASEAPPROVE_EVENT = new Event("DecreaseApprove", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DISCARD_EVENT = new Event("Discard", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event FREEZE_EVENT = new Event("Freeze", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PAUSE_EVENT = new Event("Pause", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFERFAILED_EVENT = new Event("TransferFailed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event UNPAUSE_EVENT = new Event("Unpause", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected OZCoinToken(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OZCoinToken(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OZCoinToken(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OZCoinToken(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ApprovalEventResponse> getApprovalEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getApprovalEventFromLog(log));
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public static List<DecreaseApproveEventResponse> getDecreaseApproveEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DECREASEAPPROVE_EVENT, transactionReceipt);
        ArrayList<DecreaseApproveEventResponse> responses = new ArrayList<DecreaseApproveEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DecreaseApproveEventResponse typedResponse = new DecreaseApproveEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DecreaseApproveEventResponse getDecreaseApproveEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DECREASEAPPROVE_EVENT, log);
        DecreaseApproveEventResponse typedResponse = new DecreaseApproveEventResponse();
        typedResponse.log = log;
        typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<DecreaseApproveEventResponse> decreaseApproveEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDecreaseApproveEventFromLog(log));
    }

    public Flowable<DecreaseApproveEventResponse> decreaseApproveEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DECREASEAPPROVE_EVENT));
        return decreaseApproveEventFlowable(filter);
    }

    public static List<DiscardEventResponse> getDiscardEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DISCARD_EVENT, transactionReceipt);
        ArrayList<DiscardEventResponse> responses = new ArrayList<DiscardEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DiscardEventResponse typedResponse = new DiscardEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DiscardEventResponse getDiscardEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DISCARD_EVENT, log);
        DiscardEventResponse typedResponse = new DiscardEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<DiscardEventResponse> discardEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDiscardEventFromLog(log));
    }

    public Flowable<DiscardEventResponse> discardEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DISCARD_EVENT));
        return discardEventFlowable(filter);
    }

    public static List<FreezeEventResponse> getFreezeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FREEZE_EVENT, transactionReceipt);
        ArrayList<FreezeEventResponse> responses = new ArrayList<FreezeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FreezeEventResponse typedResponse = new FreezeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FreezeEventResponse getFreezeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FREEZE_EVENT, log);
        FreezeEventResponse typedResponse = new FreezeEventResponse();
        typedResponse.log = log;
        typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<FreezeEventResponse> freezeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFreezeEventFromLog(log));
    }

    public Flowable<FreezeEventResponse> freezeEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FREEZE_EVENT));
        return freezeEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<PauseEventResponse> getPauseEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PAUSE_EVENT, transactionReceipt);
        ArrayList<PauseEventResponse> responses = new ArrayList<PauseEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PauseEventResponse typedResponse = new PauseEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PauseEventResponse getPauseEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PAUSE_EVENT, log);
        PauseEventResponse typedResponse = new PauseEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<PauseEventResponse> pauseEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPauseEventFromLog(log));
    }

    public Flowable<PauseEventResponse> pauseEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAUSE_EVENT));
        return pauseEventFlowable(filter);
    }

    public static List<TransferEventResponse> getTransferEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferEventResponse getTransferEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFER_EVENT, log);
        TransferEventResponse typedResponse = new TransferEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferEventFromLog(log));
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public static List<TransferFailedEventResponse> getTransferFailedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(TRANSFERFAILED_EVENT, transactionReceipt);
        ArrayList<TransferFailedEventResponse> responses = new ArrayList<TransferFailedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferFailedEventResponse typedResponse = new TransferFailedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static TransferFailedEventResponse getTransferFailedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(TRANSFERFAILED_EVENT, log);
        TransferFailedEventResponse typedResponse = new TransferFailedEventResponse();
        typedResponse.log = log;
        typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.reason = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<TransferFailedEventResponse> transferFailedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getTransferFailedEventFromLog(log));
    }

    public Flowable<TransferFailedEventResponse> transferFailedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERFAILED_EVENT));
        return transferFailedEventFlowable(filter);
    }

    public static List<UnpauseEventResponse> getUnpauseEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(UNPAUSE_EVENT, transactionReceipt);
        ArrayList<UnpauseEventResponse> responses = new ArrayList<UnpauseEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UnpauseEventResponse typedResponse = new UnpauseEventResponse();
            typedResponse.log = eventValues.getLog();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static UnpauseEventResponse getUnpauseEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(UNPAUSE_EVENT, log);
        UnpauseEventResponse typedResponse = new UnpauseEventResponse();
        typedResponse.log = log;
        return typedResponse;
    }

    public Flowable<UnpauseEventResponse> unpauseEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getUnpauseEventFromLog(log));
    }

    public Flowable<UnpauseEventResponse> unpauseEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UNPAUSE_EVENT));
        return unpauseEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> _allowance(String param0, String param1) {
        final Function function = new Function(FUNC__ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> allowSupportedAddress(String contractAddress) {
        final Function function = new Function(
                FUNC_ALLOWSUPPORTEDADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> allowance(String _owner, String _spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.Address(160, _spender)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burnFreezeAddressCoin(String freezeAddr,
            BigInteger _value) {
        final Function function = new Function(
                FUNC_BURNFREEZEADDRESSCOIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, freezeAddr), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> decreaseApprove(String _spender,
            BigInteger _value) {
        final Function function = new Function(
                FUNC_DECREASEAPPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> discard() {
        final Function function = new Function(
                FUNC_DISCARD, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> discarded() {
        final Function function = new Function(FUNC_DISCARDED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> exchange(String spender, String contractAddress,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_EXCHANGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.Address(160, contractAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> freezeAddress(String addr) {
        final Function function = new Function(
                FUNC_FREEZEADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getDays() {
        final Function function = new Function(FUNC_GETDAYS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> hashPermit(String to, BigInteger amount, BigInteger nonce,
            BigInteger deadline) {
        final Function function = new Function(FUNC_HASHPERMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> initialTime() {
        final Function function = new Function(FUNC_INITIALTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isFreeze(String param0) {
        final Function function = new Function(FUNC_ISFREEZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_MINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> nonces(String param0) {
        final Function function = new Function(FUNC_NONCES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> pause() {
        final Function function = new Function(
                FUNC_PAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> paused() {
        final Function function = new Function(FUNC_PAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> permitApprove(Permit permit, BigInteger v,
            byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_PERMITAPPROVE, 
                Arrays.<Type>asList(permit, 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeSupportedAddressAllow(
            String contractAddress) {
        final Function function = new Function(
                FUNC_REMOVESUPPORTEDADDRESSALLOW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> reverseExchange(String spender,
            String contractAddress, BigInteger amount) {
        final Function function = new Function(
                FUNC_REVERSEEXCHANGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.Address(160, contractAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> rollbackDiscard() {
        final Function function = new Function(
                FUNC_ROLLBACKDISCARD, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractOwner(String newOwner) {
        final Function function = new Function(
                FUNC_SETCONTRACTOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> supportedContractAddress(String param0) {
        final Function function = new Function(FUNC_SUPPORTEDCONTRACTADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String _from, String _to,
            BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unpause() {
        final Function function = new Function(
                FUNC_UNPAUSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawToken(String contractAddress,
            String spender, BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress), 
                new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static OZCoinToken load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new OZCoinToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OZCoinToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OZCoinToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OZCoinToken load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new OZCoinToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OZCoinToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OZCoinToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OZCoinToken> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String multiSignWalletAddress,
            String usdtERC20Address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, usdtERC20Address)));
        return deployRemoteCall(OZCoinToken.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<OZCoinToken> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String multiSignWalletAddress,
            String usdtERC20Address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, usdtERC20Address)));
        return deployRemoteCall(OZCoinToken.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OZCoinToken> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String multiSignWalletAddress,
            String usdtERC20Address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, usdtERC20Address)));
        return deployRemoteCall(OZCoinToken.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OZCoinToken> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String multiSignWalletAddress,
            String usdtERC20Address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, usdtERC20Address)));
        return deployRemoteCall(OZCoinToken.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class Permit extends StaticStruct {
        public String owner;

        public String spender;

        public BigInteger value;

        public BigInteger nonce;

        public BigInteger deadline;

        public Permit(String owner, String spender, BigInteger value, BigInteger nonce,
                BigInteger deadline) {
            super(new org.web3j.abi.datatypes.Address(160, owner), 
                    new org.web3j.abi.datatypes.Address(160, spender), 
                    new org.web3j.abi.datatypes.generated.Uint256(value), 
                    new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                    new org.web3j.abi.datatypes.generated.Uint256(deadline));
            this.owner = owner;
            this.spender = spender;
            this.value = value;
            this.nonce = nonce;
            this.deadline = deadline;
        }

        public Permit(Address owner, Address spender, Uint256 value, Uint256 nonce,
                Uint256 deadline) {
            super(owner, spender, value, nonce, deadline);
            this.owner = owner.getValue();
            this.spender = spender.getValue();
            this.value = value.getValue();
            this.nonce = nonce.getValue();
            this.deadline = deadline.getValue();
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String _owner;

        public String _spender;

        public BigInteger _value;
    }

    public static class DecreaseApproveEventResponse extends BaseEventResponse {
        public String _owner;

        public String _spender;

        public BigInteger _value;
    }

    public static class DiscardEventResponse extends BaseEventResponse {
    }

    public static class FreezeEventResponse extends BaseEventResponse {
        public String addr;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class PauseEventResponse extends BaseEventResponse {
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class TransferFailedEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger amount;

        public String reason;
    }

    public static class UnpauseEventResponse extends BaseEventResponse {
    }
}
