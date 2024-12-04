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
public class TotoPresaleContract extends Contract {
    public static final String BINARY = "60806040526003805460ff60a01b191690555f6006819055600755348015610025575f80fd5b50604051610cc8380380610cc88339810160408190526100449161011f565b338061006957604051631e4fbdf760e01b81525f600482015260240160405180910390fd5b610072816100b5565b50600180546001600160a01b039485166001600160a01b03199182161790915560028054938516938216939093179092556003805491909316911617905561015f565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b80516001600160a01b038116811461011a575f80fd5b919050565b5f805f60608486031215610131575f80fd5b61013a84610104565b925061014860208501610104565b915061015660408501610104565b90509250925092565b610b5c8061016c5f395ff3fe608060405234801561000f575f80fd5b50600436106100fb575f3560e01c80637a752f3211610093578063937f3bc711610063578063937f3bc7146101dd578063a34d42b8146101e6578063ea14cb26146101f9578063f2fde38b14610202575f80fd5b80637a752f321461019f5780638456cb59146101b25780638aae995a146101ba5780638da5cb5b146101c3575f80fd5b8063527eb751116100ce578063527eb7511461016757806359e55d131461017a5780635c975abb14610183578063715018a614610197575f80fd5b806301e33667146100ff5780631526993f14610127578063249b7c19146101545780633f4ba83a1461015d575b5f80fd5b61011261010d3660046109ac565b610215565b60405190151581526020015b60405180910390f35b6101466101353660046109e6565b60096020525f908152604090205481565b60405190815260200161011e565b61014660055481565b6101656102c5565b005b6101656101753660046109fd565b61037f565b61014660065481565b60035461011290600160a01b900460ff1681565b6101656106b9565b6101656101ad366004610a51565b6106cc565b610165610758565b61014660085481565b5f546040516001600160a01b03909116815260200161011e565b61014660075481565b6101126101f4366004610a80565b610819565b61014660045481565b610165610210366004610a80565b610857565b6001545f906001600160a01b0316331461024a5760405162461bcd60e51b815260040161024190610aa0565b60405180910390fd5b60405163a9059cbb60e01b81526001600160a01b0384811660048301526024820184905285169063a9059cbb906044016020604051808303815f875af1158015610296573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102ba9190610ac3565b506001949350505050565b6001546001600160a01b031633146102ef5760405162461bcd60e51b815260040161024190610aa0565b600354600160a01b900460ff166103485760405162461bcd60e51b815260206004820152601860248201527f4d757374206265207573656420756e64657220706175736500000000000000006044820152606401610241565b6003805460ff60a01b191690556040517f7805862f689e2f13df9f062ff482ad3ad112aca9e0847911ed832e158c525b33905f90a1565b600354600160a01b900460ff16156103d95760405162461bcd60e51b815260206004820152601c60248201527f4d757374206265207573656420776974686f75742070617573696e67000000006044820152606401610241565b6103e242610894565b61042e5760405162461bcd60e51b815260206004820152601960248201527f4e6f742077697468696e207468652076616c69642074696d65000000000000006044820152606401610241565b85610438816108bd565b61047c5760405162461bcd60e51b8152602060048201526015602482015274746f74616c20616d6f756e74206f766572666c6f7760581b6044820152606401610241565b610485816108e5565b6104c95760405162461bcd60e51b81526020600482015260156024820152746461696c7920616d6f756e74206f766572666c6f7760581b6044820152606401610241565b6040805160a0810182523380825230602083018181528385018c8152606085018c8152608086018c81526002549751637dcc7fd560e01b815287516001600160a01b039081166004830152945185166024820152925160448401529051606483015251608482015260ff8a1660a482015260c4810189905260e481018890529294919392911690637dcc7fd590610104015f604051808303815f87803b158015610571575f80fd5b505af1158015610583573d5f803e3d5ffd5b50506002546040516323b872dd60e01b81526001600160a01b0387811660048301528681166024830152604482018f905290911692506323b872dd91506064016020604051808303815f875af11580156105df573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906106039190610ac3565b50600354604051637c51a59360e11b81526001600160a01b038581166004830152602482018790529091169063f8a34b26906044015f604051808303815f87803b15801561064f575f80fd5b505af1158015610661573d5f803e3d5ffd5b505050508360085f8282546106769190610ae2565b9091555084905060095f61068d6201518042610b07565b81526020019081526020015f205f8282546106a89190610ae2565b909155505050505050505050505050565b6106c161091b565b6106ca5f610947565b565b6001546001600160a01b031633146106f65760405162461bcd60e51b815260040161024190610aa0565b60048490556005839055600682905560078190556040805185815260208101859052908101839052606081018290527f05ad4816a9251cdd78e46c3ab895206313233bdfda89b63d25f99415a74512c69060800160405180910390a150505050565b6001546001600160a01b031633146107825760405162461bcd60e51b815260040161024190610aa0565b600354600160a01b900460ff16156107dc5760405162461bcd60e51b815260206004820152601c60248201527f4d757374206265207573656420776974686f75742070617573696e67000000006044820152606401610241565b6003805460ff60a01b1916600160a01b1790556040517f6985a02210a168e66602d3235cb6db0e70f92b3ba4d376a33c0f3d9434bff625905f90a1565b6001545f906001600160a01b031633146108455760405162461bcd60e51b815260040161024190610aa0565b61084e82610947565b5060015b919050565b61085f61091b565b6001600160a01b03811661088857604051631e4fbdf760e01b81525f6004820152602401610241565b61089181610947565b50565b5f60045482101580156108a957506005548211155b156108b657506001919050565b505f919050565b6006545f901561084e57600654826008546108d89190610ae2565b111561084e57505f919050565b6007545f901561084e576007548260095f6109036201518042610b07565b81526020019081526020015f20546108d89190610ae2565b5f546001600160a01b031633146106ca5760405163118cdaa760e01b8152336004820152602401610241565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b80356001600160a01b0381168114610852575f80fd5b5f805f606084860312156109be575f80fd5b6109c784610996565b92506109d560208501610996565b929592945050506040919091013590565b5f602082840312156109f6575f80fd5b5035919050565b5f805f805f8060c08789031215610a12575f80fd5b863595506020870135945060408701359350606087013560ff81168114610a37575f80fd5b9598949750929560808101359460a0909101359350915050565b5f805f8060808587031215610a64575f80fd5b5050823594602084013594506040840135936060013592509050565b5f60208284031215610a90575f80fd5b610a9982610996565b9392505050565b6020808252600990820152682337b93134b23232b760b91b604082015260600190565b5f60208284031215610ad3575f80fd5b81518015158114610a99575f80fd5b80820180821115610b0157634e487b7160e01b5f52601160045260245ffd5b92915050565b5f82610b2157634e487b7160e01b5f52601260045260245ffd5b50049056fea2646970667358221220c706c9f7555944ea2fe0a80a5fa7fea967e8fce676870804d0507f29f5cf1d1664736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_PRESALEAMOUNTDAILY = "presaleAmountDaily";

    public static final String FUNC_PRESALEBEGINTIME = "presaleBeginTime";

    public static final String FUNC_PRESALEENDTIME = "presaleEndTime";

    public static final String FUNC_PRESALETOTALAMOUNT = "presaleTotalAmount";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SALE = "sale";

    public static final String FUNC_SALEAMOUNT = "saleAmount";

    public static final String FUNC_SALEAMOUNTDAILY = "saleAmountDaily";

    public static final String FUNC_SETCONTRACTOWNER = "setContractOwner";

    public static final String FUNC_SETPRESALELIMIT = "setPresaleLimit";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PAUSE_EVENT = new Event("Pause", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event SETPRESALELIMIT_EVENT = new Event("SetPresaleLimit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNPAUSE_EVENT = new Event("Unpause", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected TotoPresaleContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TotoPresaleContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TotoPresaleContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TotoPresaleContract(String contractAddress, Web3j web3j,
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

    public static List<SetPresaleLimitEventResponse> getSetPresaleLimitEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SETPRESALELIMIT_EVENT, transactionReceipt);
        ArrayList<SetPresaleLimitEventResponse> responses = new ArrayList<SetPresaleLimitEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SetPresaleLimitEventResponse typedResponse = new SetPresaleLimitEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.presaleBeginTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.presaleEndTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.presaleTotalAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.presaleAmountDaily = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SetPresaleLimitEventResponse getSetPresaleLimitEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SETPRESALELIMIT_EVENT, log);
        SetPresaleLimitEventResponse typedResponse = new SetPresaleLimitEventResponse();
        typedResponse.log = log;
        typedResponse.presaleBeginTime = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.presaleEndTime = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.presaleTotalAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.presaleAmountDaily = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<SetPresaleLimitEventResponse> setPresaleLimitEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSetPresaleLimitEventFromLog(log));
    }

    public Flowable<SetPresaleLimitEventResponse> setPresaleLimitEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SETPRESALELIMIT_EVENT));
        return setPresaleLimitEventFlowable(filter);
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

    public RemoteFunctionCall<BigInteger> presaleAmountDaily() {
        final Function function = new Function(FUNC_PRESALEAMOUNTDAILY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> presaleBeginTime() {
        final Function function = new Function(FUNC_PRESALEBEGINTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> presaleEndTime() {
        final Function function = new Function(FUNC_PRESALEENDTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> presaleTotalAmount() {
        final Function function = new Function(FUNC_PRESALETOTALAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sale(BigInteger amount, BigInteger nonce,
            BigInteger deadline, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_SALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> saleAmount() {
        final Function function = new Function(FUNC_SALEAMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> saleAmountDaily(BigInteger param0) {
        final Function function = new Function(FUNC_SALEAMOUNTDAILY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setContractOwner(String newOwner) {
        final Function function = new Function(
                FUNC_SETCONTRACTOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPresaleLimit(BigInteger beginTime,
            BigInteger endTime, BigInteger totalAmount, BigInteger amountDaily) {
        final Function function = new Function(
                FUNC_SETPRESALELIMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(beginTime), 
                new org.web3j.abi.datatypes.generated.Uint256(endTime), 
                new org.web3j.abi.datatypes.generated.Uint256(totalAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(amountDaily)), 
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
    public static TotoPresaleContract load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TotoPresaleContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TotoPresaleContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TotoPresaleContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TotoPresaleContract load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TotoPresaleContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TotoPresaleContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TotoPresaleContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TotoPresaleContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String multiSignWalletAddress,
            String ozCoinAddress, String totoCoinAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozCoinAddress), 
                new org.web3j.abi.datatypes.Address(160, totoCoinAddress)));
        return deployRemoteCall(TotoPresaleContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<TotoPresaleContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String multiSignWalletAddress, String ozCoinAddress, String totoCoinAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozCoinAddress), 
                new org.web3j.abi.datatypes.Address(160, totoCoinAddress)));
        return deployRemoteCall(TotoPresaleContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TotoPresaleContract> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String multiSignWalletAddress,
            String ozCoinAddress, String totoCoinAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozCoinAddress), 
                new org.web3j.abi.datatypes.Address(160, totoCoinAddress)));
        return deployRemoteCall(TotoPresaleContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<TotoPresaleContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String multiSignWalletAddress, String ozCoinAddress, String totoCoinAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozCoinAddress), 
                new org.web3j.abi.datatypes.Address(160, totoCoinAddress)));
        return deployRemoteCall(TotoPresaleContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class PauseEventResponse extends BaseEventResponse {
    }

    public static class SetPresaleLimitEventResponse extends BaseEventResponse {
        public BigInteger presaleBeginTime;

        public BigInteger presaleEndTime;

        public BigInteger presaleTotalAmount;

        public BigInteger presaleAmountDaily;
    }

    public static class UnpauseEventResponse extends BaseEventResponse {
    }
}
