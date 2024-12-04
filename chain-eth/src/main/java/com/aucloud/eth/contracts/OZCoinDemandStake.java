package com.aucloud.eth.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
public class OZCoinDemandStake extends Contract {
    public static final String BINARY = "608060405234801561000f575f80fd5b5060405161130738038061130783398101604081905261002e91610109565b338061005357604051631e4fbdf760e01b81525f600482015260240160405180910390fd5b61005c8161009f565b50600680546001600160a01b039485166001600160a01b031991821617909155600580549385169382169390931790925560078054919093169116179055610149565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b80516001600160a01b0381168114610104575f80fd5b919050565b5f805f6060848603121561011b575f80fd5b610124846100ee565b9250610132602085016100ee565b9150610140604085016100ee565b90509250925092565b6111b1806101565f395ff3fe608060405234801561000f575f80fd5b50600436106100e5575f3560e01c8063a203cd5a11610088578063b604a19411610063578063b604a194146101c1578063b8e85fec146101ca578063e57466fd1461022d578063f2fde38b146102b2575f80fd5b8063a203cd5a14610184578063a34d42b814610197578063acebba99146101aa575f80fd5b80637022b58e116100c35780637022b58e14610151578063715018a6146101595780638da5cb5b146101615780638df8280014610171575f80fd5b806301e33667146100e95780631e2eb088146100fe578063616bcd211461012e575b5f80fd5b6100fc6100f7366004610f42565b6102c5565b005b61011161010c366004610f7c565b610386565b6040516001600160a01b0390911681526020015b60405180910390f35b61014161013c366004610f93565b6103ae565b6040519015158152602001610125565b61014161067b565b6100fc61083d565b5f546001600160a01b0316610111565b6100fc61017f366004610f7c565b610850565b610141610192366004610fbb565b610a8d565b6101416101a536600461100f565b610be6565b6101b360045481565b604051908152602001610125565b6101b360015481565b6101dd6101d836600461100f565b610c40565b604051610125919081516001600160a01b031681526020808301519082015260408083015190820152606080830151908201526080808301519082015260a0918201519181019190915260c00190565b61027b61023b36600461100f565b600260208190525f91825260409091208054600182015492820154600383015460048401546005909401546001600160a01b039093169493919290919086565b604080516001600160a01b0390971687526020870195909552938501929092526060840152608083015260a082015260c001610125565b6100fc6102c036600461100f565b610d16565b6006546001600160a01b031633146103105760405162461bcd60e51b81526020600482015260096024820152682337b93134b23232b760b91b60448201526064015b60405180910390fd5b60405163a9059cbb60e01b81526001600160a01b0383811660048301526024820183905284169063a9059cbb906044016020604051808303815f875af115801561035c573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906103809190611028565b50505050565b60038181548110610395575f80fd5b5f918252602090912001546001600160a01b0316905081565b6001600160a01b038083165f908152600260205260408120805491929091166104075760405162461bcd60e51b815260206004820152600b60248201526a139bdb995e1a5cdd195b9d60aa1b6044820152606401610307565b5f83118015610429575080600101548160020154610425919061105b565b8311155b61046c5760405162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b6044820152606401610307565b60028101548390811015610481575082610488565b5060028101545b60055460405163a9059cbb60e01b81526001600160a01b038781166004830152602482018490529091169063a9059cbb906044016020604051808303815f875af11580156104d8573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906104fc9190611028565b50600282015461050c9082610d53565b600283015561051b818561106e565b9050801561066e5760055460405163a9059cbb60e01b81526001600160a01b038781166004830152602482018490529091169063a9059cbb906044016020604051808303815f875af1158015610573573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105979190611028565b505f6127108360010154836127106105af9190611081565b6105b99190611098565b84600301546105c89190611081565b6105d29190611098565b60075460405163639c668b60e01b81529192506001600160a01b03169063639c668b90610607906004908a90869083016110d7565b5f604051808303815f87803b15801561061e575f80fd5b505af1158015610630573d5f803e3d5ffd5b5050506001840154610643915083610d53565b600184015560038301546106579082610d53565b60038401556001546106699083610d53565b600155505b6001925050505b92915050565b5f610684610d75565b6003545f61069360018361106e565b90505b5f60025f600384815481106106ad576106ad6110ff565b5f9182526020808320909101546001600160a01b031683528201929092526040019020600281015460018201549192506106e79190610da1565b6001808301919091556002820154905461070091610da1565b60019081555f6002830181905590820154900361082a5760025f6003848154811061072d5761072d6110ff565b5f9182526020808320909101546001600160a01b03168352820192909252604001812080546001600160a01b0319168155600181810183905560028201839055600380830184905560048301849055600590920192909255805490916107929161106e565b815481106107a2576107a26110ff565b5f91825260209091200154600380546001600160a01b0390921691849081106107cd576107cd6110ff565b905f5260205f20015f6101000a8154816001600160a01b0302191690836001600160a01b03160217905550600380548061080957610809611113565b5f8281526020902081015f1990810180546001600160a01b03191690550190555b508061083581611127565b915050610696565b610845610d75565b61084e5f610dbf565b565b610858610d75565b5f61086282610e0e565b905060045481116108a65760405162461bcd60e51b815260206004820152600e60248201526d496e2074686520636f6f6c696e6760901b6044820152606401610307565b5f600154116108e55760405162461bcd60e51b815260206004820152600b60248201526a7374616b6520656d70747960a81b6044820152606401610307565b600754604051632596038360e11b8152600481018490525f916001600160a01b031690634b2c070690602401602060405180830381865afa15801561092c573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610950919061113c565b6007549091505f906001600160a01b031663be96a8bd61097160018561106e565b60046040518363ffffffff1660e01b8152600401610990929190611153565b602060405180830381865afa1580156109ab573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906109cf919061113c565b6003549091505f6109e160018361106e565b90505b5f60025f600384815481106109fb576109fb6110ff565b5f9182526020808320909101546001600160a01b03168352820192909252604001902060018101549091508015610a78575f61271060015483612710610a419190611081565b610a4b9190611098565b610a559088611081565b610a5f9190611098565b6003840154909150610a719082610da1565b6003840155505b50508080610a8590611127565b9150506109e4565b6040805160a0810182523380825230602083018181528385018b8152606085018b8152608086018b81526005549751637dcc7fd560e01b815287516001600160a01b039081166004830152945185166024820152925160448401529051606483015251608482015260ff891660a482015260c4810188905260e481018790525f95939492939190911690637dcc7fd590610104015f604051808303815f87803b158015610b38575f80fd5b505af1158015610b4a573d5f803e3d5ffd5b50506005546040516323b872dd60e01b81526001600160a01b0387811660048301528681166024830152604482018f905290911692506323b872dd91506064016020604051808303815f875af1158015610ba6573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610bca9190611028565b50610bd5838b610e1c565b5060019a9950505050505050505050565b6006545f906001600160a01b03163314610c2e5760405162461bcd60e51b81526020600482015260096024820152682337b93134b23232b760b91b6044820152606401610307565b610c3782610dbf565b5060015b919050565b610c7c6040518060c001604052805f6001600160a01b031681526020015f81526020015f81526020015f81526020015f81526020015f81525090565b6001600160a01b038083165f90815260026020818152604092839020835160c0810185528154909516808652600182015492860192909252918201549284019290925260038101546060840152600481015460808401526005015460a08301526106755760405162461bcd60e51b815260206004820152600b60248201526a139bdb995e1a5cdd195b9d60aa1b6044820152606401610307565b610d1e610d75565b6001600160a01b038116610d4757604051631e4fbdf760e01b81525f6004820152602401610307565b610d5081610dbf565b50565b5f82821115610d6457610d64611167565b610d6e828461106e565b9392505050565b5f546001600160a01b0316331461084e5760405163118cdaa760e01b8152336004820152602401610307565b5f80610dad838561105b565b905083811015610d6e57610d6e611167565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b5f6106756201518083611098565b6001600160a01b038083165f9081526002602052604081208054919290911615610e59576002810154610e4f9084610da1565b6002820155610f22565b6040805160c0810182526001600160a01b038087168083525f60208085018281528587018a815260608701848152608088018581524260a08a019081528787526002958690529986209851895498166001600160a01b031998891617895592516001808a0191909155915193880193909355915160038088019190915590516004870155955160059095019490945584549384018555939093527fc2575a0e9e593c00f959f8c92f12db2869c3395a3b0502d05e2516446f71f85b909101805490911690911790555b5060019392505050565b80356001600160a01b0381168114610c3b575f80fd5b5f805f60608486031215610f54575f80fd5b610f5d84610f2c565b9250610f6b60208501610f2c565b929592945050506040919091013590565b5f60208284031215610f8c575f80fd5b5035919050565b5f8060408385031215610fa4575f80fd5b610fad83610f2c565b946020939093013593505050565b5f805f805f8060c08789031215610fd0575f80fd5b863595506020870135945060408701359350606087013560ff81168114610ff5575f80fd5b9598949750929560808101359460a0909101359350915050565b5f6020828403121561101f575f80fd5b610d6e82610f2c565b5f60208284031215611038575f80fd5b81518015158114610d6e575f80fd5b634e487b7160e01b5f52601160045260245ffd5b8082018082111561067557610675611047565b8181038181111561067557610675611047565b808202811582820484141761067557610675611047565b5f826110b257634e487b7160e01b5f52601260045260245ffd5b500490565b600781106110d357634e487b7160e01b5f52602160045260245ffd5b9052565b606081016110e582866110b7565b6001600160a01b0393909316602082015260400152919050565b634e487b7160e01b5f52603260045260245ffd5b634e487b7160e01b5f52603160045260245ffd5b5f8161113557611135611047565b505f190190565b5f6020828403121561114c575f80fd5b5051919050565b82815260408101610d6e60208301846110b7565b634e487b7160e01b5f52600160045260245ffdfea2646970667358221220bb4f304ec617d47ec26c876be388c757bcd055d06c1d7c345def7e887d2a4a7464736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ACCOUNTKEYS = "accountKeys";

    public static final String FUNC_CONFIRM = "confirm";

    public static final String FUNC_GETACCOUNTBYADDRESS = "getAccountByAddress";

    public static final String FUNC_LASTSETTLEPERIOD = "lastSettlePeriod";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_OZCTOTALINSTOCKAMOUNT = "ozcTotalInStockAmount";

    public static final String FUNC_REDEMPTION = "redemption";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETCONTRACTOWNER = "setContractOwner";

    public static final String FUNC_SETTLE = "settle";

    public static final String FUNC_STAKE = "stake";

    public static final String FUNC_STAKEACCOUNTS = "stakeAccounts";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected OZCoinDemandStake(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OZCoinDemandStake(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OZCoinDemandStake(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OZCoinDemandStake(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
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

    public RemoteFunctionCall<String> accountKeys(BigInteger param0) {
        final Function function = new Function(FUNC_ACCOUNTKEYS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> confirm() {
        final Function function = new Function(
                FUNC_CONFIRM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Account> getAccountByAddress(String accountAddress) {
        final Function function = new Function(FUNC_GETACCOUNTBYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, accountAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Account>() {}));
        return executeRemoteCallSingleValueReturn(function, Account.class);
    }

    public RemoteFunctionCall<BigInteger> lastSettlePeriod() {
        final Function function = new Function(FUNC_LASTSETTLEPERIOD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> ozcTotalInStockAmount() {
        final Function function = new Function(FUNC_OZCTOTALINSTOCKAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> redemption(String accountAddress,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_REDEMPTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, accountAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
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

    public RemoteFunctionCall<TransactionReceipt> setContractOwner(String newOwner) {
        final Function function = new Function(
                FUNC_SETCONTRACTOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> settle(BigInteger timestamp) {
        final Function function = new Function(
                FUNC_SETTLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> stake(BigInteger amount, BigInteger nonce,
            BigInteger deadline, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_STAKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> stakeAccounts(
            String param0) {
        final Function function = new Function(FUNC_STAKEACCOUNTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple6<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple6<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawToken(String contractAddress,
            String targetAddress, BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress), 
                new org.web3j.abi.datatypes.Address(160, targetAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static OZCoinDemandStake load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OZCoinDemandStake(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OZCoinDemandStake load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OZCoinDemandStake(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OZCoinDemandStake load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OZCoinDemandStake(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OZCoinDemandStake load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OZCoinDemandStake(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OZCoinDemandStake> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String multiSignWalletAddress,
            String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinDemandStake.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<OZCoinDemandStake> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String multiSignWalletAddress, String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinDemandStake.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OZCoinDemandStake> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String multiSignWalletAddress,
            String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinDemandStake.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OZCoinDemandStake> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String multiSignWalletAddress, String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinDemandStake.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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

    public static class Account extends StaticStruct {
        public String accountAddress;

        public BigInteger ozcInStockAmount;

        public BigInteger ozcInTransitAmount;

        public BigInteger totoIncomeAmount;

        public BigInteger stakeDays;

        public BigInteger firstStakeTimestamp;

        public Account(String accountAddress, BigInteger ozcInStockAmount,
                BigInteger ozcInTransitAmount, BigInteger totoIncomeAmount, BigInteger stakeDays,
                BigInteger firstStakeTimestamp) {
            super(new org.web3j.abi.datatypes.Address(160, accountAddress), 
                    new org.web3j.abi.datatypes.generated.Uint256(ozcInStockAmount), 
                    new org.web3j.abi.datatypes.generated.Uint256(ozcInTransitAmount), 
                    new org.web3j.abi.datatypes.generated.Uint256(totoIncomeAmount), 
                    new org.web3j.abi.datatypes.generated.Uint256(stakeDays), 
                    new org.web3j.abi.datatypes.generated.Uint256(firstStakeTimestamp));
            this.accountAddress = accountAddress;
            this.ozcInStockAmount = ozcInStockAmount;
            this.ozcInTransitAmount = ozcInTransitAmount;
            this.totoIncomeAmount = totoIncomeAmount;
            this.stakeDays = stakeDays;
            this.firstStakeTimestamp = firstStakeTimestamp;
        }

        public Account(Address accountAddress, Uint256 ozcInStockAmount, Uint256 ozcInTransitAmount,
                Uint256 totoIncomeAmount, Uint256 stakeDays, Uint256 firstStakeTimestamp) {
            super(accountAddress, ozcInStockAmount, ozcInTransitAmount, totoIncomeAmount, stakeDays, firstStakeTimestamp);
            this.accountAddress = accountAddress.getValue();
            this.ozcInStockAmount = ozcInStockAmount.getValue();
            this.ozcInTransitAmount = ozcInTransitAmount.getValue();
            this.totoIncomeAmount = totoIncomeAmount.getValue();
            this.stakeDays = stakeDays.getValue();
            this.firstStakeTimestamp = firstStakeTimestamp.getValue();
        }
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
