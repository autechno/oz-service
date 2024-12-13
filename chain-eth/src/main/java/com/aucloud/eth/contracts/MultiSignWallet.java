package com.aucloud.eth.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
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
public class MultiSignWallet extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDADMIN = "addAdmin";

    public static final String FUNC_ADDSUPERADMIN = "addSuperAdmin";

    public static final String FUNC_ADMINTYPE = "adminType";

    public static final String FUNC_ADMINS = "admins";

    public static final String FUNC_CONFIGTRANSACTIONTYPE = "configTransactionType";

    public static final String FUNC_CONFIRMTRANSACTION = "confirmTransaction";

    public static final String FUNC_INPUTDATADECODE = "inputDataDecode";

    public static final String FUNC_REMOVEADMIN = "removeAdmin";

    public static final String FUNC_REMOVESUPERADMIN = "removeSuperAdmin";

    public static final String FUNC_SUBMITTRANSACTION = "submitTransaction";

    public static final String FUNC_SUPERADMINS = "superAdmins";

    public static final String FUNC_TRANSACTIONADMINCONFIRMCOUNT = "transactionAdminConfirmCount";

    public static final String FUNC_TRANSACTIONCONFIRM = "transactionConfirm";

    public static final String FUNC_TRANSACTIONSUPERADMINCONFIRMCOUNT = "transactionSuperAdminConfirmCount";

    public static final String FUNC_VIEWADMINS = "viewAdmins";

    public static final String FUNC_VIEWSUPERADMINS = "viewSuperAdmins";

    public static final String FUNC_VIEWTRANSACTION = "viewTransaction";

    public static final Event CONFIRMTRANSACTION_EVENT = new Event("ConfirmTransaction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event EXECUTETRANSACTION_EVENT = new Event("ExecuteTransaction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event SUBMITTRANSACTION_EVENT = new Event("SubmitTransaction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected MultiSignWallet(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MultiSignWallet(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MultiSignWallet(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MultiSignWallet(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ConfirmTransactionEventResponse> getConfirmTransactionEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CONFIRMTRANSACTION_EVENT, transactionReceipt);
        ArrayList<ConfirmTransactionEventResponse> responses = new ArrayList<ConfirmTransactionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ConfirmTransactionEventResponse typedResponse = new ConfirmTransactionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.admin = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.transactionId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ConfirmTransactionEventResponse getConfirmTransactionEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CONFIRMTRANSACTION_EVENT, log);
        ConfirmTransactionEventResponse typedResponse = new ConfirmTransactionEventResponse();
        typedResponse.log = log;
        typedResponse.admin = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.transactionId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ConfirmTransactionEventResponse> confirmTransactionEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getConfirmTransactionEventFromLog(log));
    }

    public Flowable<ConfirmTransactionEventResponse> confirmTransactionEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONFIRMTRANSACTION_EVENT));
        return confirmTransactionEventFlowable(filter);
    }

    public static List<ExecuteTransactionEventResponse> getExecuteTransactionEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EXECUTETRANSACTION_EVENT, transactionReceipt);
        ArrayList<ExecuteTransactionEventResponse> responses = new ArrayList<ExecuteTransactionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ExecuteTransactionEventResponse typedResponse = new ExecuteTransactionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.transactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ExecuteTransactionEventResponse getExecuteTransactionEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EXECUTETRANSACTION_EVENT, log);
        ExecuteTransactionEventResponse typedResponse = new ExecuteTransactionEventResponse();
        typedResponse.log = log;
        typedResponse.transactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<ExecuteTransactionEventResponse> executeTransactionEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getExecuteTransactionEventFromLog(log));
    }

    public Flowable<ExecuteTransactionEventResponse> executeTransactionEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXECUTETRANSACTION_EVENT));
        return executeTransactionEventFlowable(filter);
    }

    public static List<SubmitTransactionEventResponse> getSubmitTransactionEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SUBMITTRANSACTION_EVENT, transactionReceipt);
        ArrayList<SubmitTransactionEventResponse> responses = new ArrayList<SubmitTransactionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SubmitTransactionEventResponse typedResponse = new SubmitTransactionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.transactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SubmitTransactionEventResponse getSubmitTransactionEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SUBMITTRANSACTION_EVENT, log);
        SubmitTransactionEventResponse typedResponse = new SubmitTransactionEventResponse();
        typedResponse.log = log;
        typedResponse.transactionId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<SubmitTransactionEventResponse> submitTransactionEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSubmitTransactionEventFromLog(log));
    }

    public Flowable<SubmitTransactionEventResponse> submitTransactionEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUBMITTRANSACTION_EVENT));
        return submitTransactionEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addAdmin(String admin) {
        final Function function = new Function(
                FUNC_ADDADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addSuperAdmin(String superAdmin) {
        final Function function = new Function(
                FUNC_ADDSUPERADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, superAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> adminType(String param0) {
        final Function function = new Function(FUNC_ADMINTYPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> admins(BigInteger param0) {
        final Function function = new Function(FUNC_ADMINS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> configTransactionType(byte[] method,
            BigInteger adminConfirm, BigInteger superAdminConfirm) {
        final Function function = new Function(
                FUNC_CONFIGTRANSACTIONTYPE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(method), 
                new org.web3j.abi.datatypes.generated.Uint8(adminConfirm), 
                new org.web3j.abi.datatypes.generated.Uint8(superAdminConfirm)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> confirmTransaction(
            BigInteger confirmTransactionId) {
        final Function function = new Function(
                FUNC_CONFIRMTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(confirmTransactionId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> inputDataDecode(byte[] data) {
        final Function function = new Function(FUNC_INPUTDATADECODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAdmin(String admin) {
        final Function function = new Function(
                FUNC_REMOVEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, admin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeSuperAdmin(String superAdmin) {
        final Function function = new Function(
                FUNC_REMOVESUPERADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, superAdmin)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> submitTransaction(String targetAddress,
            byte[] data) {
        final Function function = new Function(
                FUNC_SUBMITTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, targetAddress), 
                new org.web3j.abi.datatypes.DynamicBytes(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> superAdmins(BigInteger param0) {
        final Function function = new Function(FUNC_SUPERADMINS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> transactionAdminConfirmCount(BigInteger param0) {
        final Function function = new Function(FUNC_TRANSACTIONADMINCONFIRMCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> transactionConfirm(BigInteger param0, String param1) {
        final Function function = new Function(FUNC_TRANSACTIONCONFIRM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> transactionSuperAdminConfirmCount(BigInteger param0) {
        final Function function = new Function(FUNC_TRANSACTIONSUPERADMINCONFIRMCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> viewAdmins() {
        final Function function = new Function(FUNC_VIEWADMINS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<List> viewSuperAdmins() {
        final Function function = new Function(FUNC_VIEWSUPERADMINS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<Transaction> viewTransaction(BigInteger viewTransactionId) {
        final Function function = new Function(FUNC_VIEWTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(viewTransactionId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Transaction>() {}));
        return executeRemoteCallSingleValueReturn(function, Transaction.class);
    }

    @Deprecated
    public static MultiSignWallet load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new MultiSignWallet(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MultiSignWallet load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MultiSignWallet(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MultiSignWallet load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new MultiSignWallet(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MultiSignWallet load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MultiSignWallet(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class Transaction extends DynamicStruct {
        public String targetAddress;

        public byte[] data;

        public byte[] transactionType;

        public BigInteger status;

        public Transaction(String targetAddress, byte[] data, byte[] transactionType,
                BigInteger status) {
            super(new org.web3j.abi.datatypes.Address(160, targetAddress), 
                    new org.web3j.abi.datatypes.DynamicBytes(data), 
                    new org.web3j.abi.datatypes.generated.Bytes4(transactionType), 
                    new org.web3j.abi.datatypes.generated.Uint8(status));
            this.targetAddress = targetAddress;
            this.data = data;
            this.transactionType = transactionType;
            this.status = status;
        }

        public Transaction(Address targetAddress, DynamicBytes data, Bytes4 transactionType,
                Uint8 status) {
            super(targetAddress, data, transactionType, status);
            this.targetAddress = targetAddress.getValue();
            this.data = data.getValue();
            this.transactionType = transactionType.getValue();
            this.status = status.getValue();
        }
    }

    public static class ConfirmTransactionEventResponse extends BaseEventResponse {
        public String admin;

        public BigInteger transactionId;
    }

    public static class ExecuteTransactionEventResponse extends BaseEventResponse {
        public BigInteger transactionId;
    }

    public static class SubmitTransactionEventResponse extends BaseEventResponse {
        public BigInteger transactionId;
    }
}
