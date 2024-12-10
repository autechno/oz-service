package com.aucloud.eth.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public class TotoCoinToken extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_PRODUCE_PERIOD = "PRODUCE_PERIOD";

    public static final String FUNC__ALLOWANCE = "_allowance";

    public static final String FUNC_ADDAUTHORIZEDCONTRACTADDRESS = "addAuthorizedContractAddress";

    public static final String FUNC_ADDEXCHARGECONTRACTADDRESS = "addExchargeContractAddress";

    public static final String FUNC_ALLOWEXCHANGE = "allowExchange";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_AUTHORIZEDCONTRACTADDRESS = "authorizedContractAddress";

    public static final String FUNC_AUTOAIRDROP = "autoAirDrop";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BURNPOOL = "burnPool";

    public static final String FUNC_CONFIGUREPOOLAUTOADDRESS = "configurePoolAutoAddress";

    public static final String FUNC_DAYPOOLPRODUCTION = "dayPoolProduction";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_DECREASEAPPROVE = "decreaseApprove";

    public static final String FUNC_DISTRIBUTE = "distribute";

    public static final String FUNC_EXCHANGE = "exchange";

    public static final String FUNC_GETDAYS = "getDays";

    public static final String FUNC_GETPERIOD = "getPeriod";

    public static final String FUNC_GETPOOLPRODUCTINBYPERIOD = "getPoolProductinByPeriod";

    public static final String FUNC_INITIALPERIOD = "initialPeriod";

    public static final String FUNC_LASTPRODUCEPERIOD = "lastProducePeriod";

    public static final String FUNC_LASTPRODUCTION = "lastProduction";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_NEXTPRODUCTION = "nextProduction";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PAUSE = "pause";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_POOLDISTRIBUTEPROPORTION = "poolDistributeProportion";

    public static final String FUNC_PRESALE = "presale";

    public static final String FUNC_PRODUCE = "produce";

    public static final String FUNC_PRODUCTIONLIMIT = "productionLimit";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETCONTRACTOWNER = "setContractOwner";

    public static final String FUNC_SETNEXTPRODUCTION = "setNextProduction";

    public static final String FUNC_SETPOOLDISTRIBUTEPROPORTION = "setPoolDistributeProportion";

    public static final String FUNC_SETPRODUCTIONLIMIT = "setProductionLimit";

    public static final String FUNC_SUBAUTHORIZEDCONTRACTADDRESS = "subAuthorizedContractAddress";

    public static final String FUNC_SUBEXCHARGECONTRACTADDRESS = "subExchargeContractAddress";

    public static final String FUNC_SUPPORTEDEXCHARGECONTRACTADDRESS = "supportedExchargeContractAddress";

    public static final String FUNC_SWITCHEXCHANGE = "switchExchange";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_TRANSFERSTAKEPOOL = "transferStakePool";

    public static final String FUNC_UNPAUSE = "unpause";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event DECREASEAPPROVE_EVENT = new Event("DecreaseApprove", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event NEXTPRODUCTIONCHANGE_EVENT = new Event("NextProductionChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PAUSE_EVENT = new Event("Pause", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final Event POOLAUTOADDRESSCHANGE_EVENT = new Event("PoolAutoAddressChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event POOLDISTRIBUTEPROPORTIONCHANGE_EVENT = new Event("PoolDistributeProportionChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PRODUCTIONLIMITCHANGE_EVENT = new Event("ProductionLimitChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event UNPAUSE_EVENT = new Event("Unpause", 
            Arrays.<TypeReference<?>>asList());
    ;

    @Deprecated
    protected TotoCoinToken(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TotoCoinToken(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TotoCoinToken(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TotoCoinToken(String contractAddress, Web3j web3j,
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
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ApprovalEventResponse getApprovalEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(APPROVAL_EVENT, log);
        ApprovalEventResponse typedResponse = new ApprovalEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public static List<NextProductionChangeEventResponse> getNextProductionChangeEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(NEXTPRODUCTIONCHANGE_EVENT, transactionReceipt);
        ArrayList<NextProductionChangeEventResponse> responses = new ArrayList<NextProductionChangeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NextProductionChangeEventResponse typedResponse = new NextProductionChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.beforeValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.afterValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static NextProductionChangeEventResponse getNextProductionChangeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(NEXTPRODUCTIONCHANGE_EVENT, log);
        NextProductionChangeEventResponse typedResponse = new NextProductionChangeEventResponse();
        typedResponse.log = log;
        typedResponse.beforeValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.afterValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<NextProductionChangeEventResponse> nextProductionChangeEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getNextProductionChangeEventFromLog(log));
    }

    public Flowable<NextProductionChangeEventResponse> nextProductionChangeEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEXTPRODUCTIONCHANGE_EVENT));
        return nextProductionChangeEventFlowable(filter);
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

    public static List<PoolAutoAddressChangeEventResponse> getPoolAutoAddressChangeEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POOLAUTOADDRESSCHANGE_EVENT, transactionReceipt);
        ArrayList<PoolAutoAddressChangeEventResponse> responses = new ArrayList<PoolAutoAddressChangeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PoolAutoAddressChangeEventResponse typedResponse = new PoolAutoAddressChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.beforeValue = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.afterValue = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PoolAutoAddressChangeEventResponse getPoolAutoAddressChangeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POOLAUTOADDRESSCHANGE_EVENT, log);
        PoolAutoAddressChangeEventResponse typedResponse = new PoolAutoAddressChangeEventResponse();
        typedResponse.log = log;
        typedResponse.beforeValue = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.afterValue = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PoolAutoAddressChangeEventResponse> poolAutoAddressChangeEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPoolAutoAddressChangeEventFromLog(log));
    }

    public Flowable<PoolAutoAddressChangeEventResponse> poolAutoAddressChangeEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POOLAUTOADDRESSCHANGE_EVENT));
        return poolAutoAddressChangeEventFlowable(filter);
    }

    public static List<PoolDistributeProportionChangeEventResponse> getPoolDistributeProportionChangeEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(POOLDISTRIBUTEPROPORTIONCHANGE_EVENT, transactionReceipt);
        ArrayList<PoolDistributeProportionChangeEventResponse> responses = new ArrayList<PoolDistributeProportionChangeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PoolDistributeProportionChangeEventResponse typedResponse = new PoolDistributeProportionChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.poolId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.proportion = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PoolDistributeProportionChangeEventResponse getPoolDistributeProportionChangeEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(POOLDISTRIBUTEPROPORTIONCHANGE_EVENT, log);
        PoolDistributeProportionChangeEventResponse typedResponse = new PoolDistributeProportionChangeEventResponse();
        typedResponse.log = log;
        typedResponse.poolId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.proportion = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PoolDistributeProportionChangeEventResponse> poolDistributeProportionChangeEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPoolDistributeProportionChangeEventFromLog(log));
    }

    public Flowable<PoolDistributeProportionChangeEventResponse> poolDistributeProportionChangeEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(POOLDISTRIBUTEPROPORTIONCHANGE_EVENT));
        return poolDistributeProportionChangeEventFlowable(filter);
    }

    public static List<ProductionLimitChangeEventResponse> getProductionLimitChangeEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PRODUCTIONLIMITCHANGE_EVENT, transactionReceipt);
        ArrayList<ProductionLimitChangeEventResponse> responses = new ArrayList<ProductionLimitChangeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProductionLimitChangeEventResponse typedResponse = new ProductionLimitChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.beforeValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.afterValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ProductionLimitChangeEventResponse getProductionLimitChangeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PRODUCTIONLIMITCHANGE_EVENT, log);
        ProductionLimitChangeEventResponse typedResponse = new ProductionLimitChangeEventResponse();
        typedResponse.log = log;
        typedResponse.beforeValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.afterValue = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<ProductionLimitChangeEventResponse> productionLimitChangeEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getProductionLimitChangeEventFromLog(log));
    }

    public Flowable<ProductionLimitChangeEventResponse> productionLimitChangeEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PRODUCTIONLIMITCHANGE_EVENT));
        return productionLimitChangeEventFlowable(filter);
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

    public RemoteFunctionCall<BigInteger> PRODUCE_PERIOD() {
        final Function function = new Function(FUNC_PRODUCE_PERIOD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> _allowance(String param0, String param1) {
        final Function function = new Function(FUNC__ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addAuthorizedContractAddress(
            String contractAddress) {
        final Function function = new Function(
                FUNC_ADDAUTHORIZEDCONTRACTADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addExchargeContractAddress(
            String contractAddress) {
        final Function function = new Function(
                FUNC_ADDEXCHARGECONTRACTADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> allowExchange() {
        final Function function = new Function(FUNC_ALLOWEXCHANGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteFunctionCall<BigInteger> authorizedContractAddress(String param0) {
        final Function function = new Function(FUNC_AUTHORIZEDCONTRACTADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> autoAirDrop(BigInteger poolId,
            List<TransferInfo> transferInfos) {
        final Function function = new Function(
                FUNC_AUTOAIRDROP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(poolId), 
                new org.web3j.abi.datatypes.DynamicArray<TransferInfo>(TransferInfo.class, transferInfos)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> burnPool(BigInteger poolId) {
        final Function function = new Function(
                FUNC_BURNPOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(poolId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> configurePoolAutoAddress(BigInteger poolId,
            String autoAirDropAddress) {
        final Function function = new Function(
                FUNC_CONFIGUREPOOLAUTOADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(poolId), 
                new org.web3j.abi.datatypes.Address(160, autoAirDropAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> dayPoolProduction(BigInteger param0, BigInteger param1) {
        final Function function = new Function(FUNC_DAYPOOLPRODUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<TransactionReceipt> distribute(BigInteger poolId,
            List<TransferInfo> transferInfos) {
        final Function function = new Function(
                FUNC_DISTRIBUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(poolId), 
                new org.web3j.abi.datatypes.DynamicArray<TransferInfo>(TransferInfo.class, transferInfos)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<BigInteger> getDays() {
        final Function function = new Function(FUNC_GETDAYS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPeriod(BigInteger timestamp) {
        final Function function = new Function(FUNC_GETPERIOD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPoolProductinByPeriod(BigInteger period,
            BigInteger poolId) {
        final Function function = new Function(FUNC_GETPOOLPRODUCTINBYPERIOD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(period), 
                new org.web3j.abi.datatypes.generated.Uint8(poolId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> initialPeriod() {
        final Function function = new Function(FUNC_INITIALPERIOD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> lastProducePeriod() {
        final Function function = new Function(FUNC_LASTPRODUCEPERIOD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> lastProduction() {
        final Function function = new Function(FUNC_LASTPRODUCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<BigInteger> nextProduction() {
        final Function function = new Function(FUNC_NEXTPRODUCTION, 
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

    public RemoteFunctionCall<BigInteger> poolDistributeProportion(BigInteger param0) {
        final Function function = new Function(FUNC_POOLDISTRIBUTEPROPORTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> presale(String spender, BigInteger amount) {
        final Function function = new Function(
                FUNC_PRESALE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> produce(BigInteger timestamp) {
        final Function function = new Function(
                FUNC_PRODUCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(timestamp)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> productionLimit() {
        final Function function = new Function(FUNC_PRODUCTIONLIMIT, 
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

    public RemoteFunctionCall<TransactionReceipt> setContractOwner(String newOwner) {
        final Function function = new Function(
                FUNC_SETCONTRACTOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setNextProduction(BigInteger productionAmount) {
        final Function function = new Function(
                FUNC_SETNEXTPRODUCTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productionAmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPoolDistributeProportion(
            BigInteger ozGroupProportion, BigInteger ozSupportProportion,
            BigInteger ozFundProportion, BigInteger stakeProportion, BigInteger ozbetProportion,
            BigInteger ozbetVipProportion) {
        final Function function = new Function(
                FUNC_SETPOOLDISTRIBUTEPROPORTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(ozGroupProportion), 
                new org.web3j.abi.datatypes.generated.Uint256(ozSupportProportion), 
                new org.web3j.abi.datatypes.generated.Uint256(ozFundProportion), 
                new org.web3j.abi.datatypes.generated.Uint256(stakeProportion), 
                new org.web3j.abi.datatypes.generated.Uint256(ozbetProportion), 
                new org.web3j.abi.datatypes.generated.Uint256(ozbetVipProportion)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setProductionLimit(BigInteger productionLimitV) {
        final Function function = new Function(
                FUNC_SETPRODUCTIONLIMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(productionLimitV)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> subAuthorizedContractAddress(
            String contractAddress) {
        final Function function = new Function(
                FUNC_SUBAUTHORIZEDCONTRACTADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> subExchargeContractAddress(
            String contractAddress) {
        final Function function = new Function(
                FUNC_SUBEXCHARGECONTRACTADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> supportedExchargeContractAddress(String param0) {
        final Function function = new Function(FUNC_SUPPORTEDEXCHARGECONTRACTADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> switchExchange() {
        final Function function = new Function(
                FUNC_SWITCHEXCHANGE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<TransactionReceipt> transferStakePool(BigInteger poolId,
            String spender, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFERSTAKEPOOL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(poolId), 
                new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
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
    public static TotoCoinToken load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new TotoCoinToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TotoCoinToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TotoCoinToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TotoCoinToken load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new TotoCoinToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TotoCoinToken load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TotoCoinToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class TransferInfo extends StaticStruct {
        public String spender;

        public BigInteger amount;

        public TransferInfo(String spender, BigInteger amount) {
            super(new org.web3j.abi.datatypes.Address(160, spender), 
                    new org.web3j.abi.datatypes.generated.Uint256(amount));
            this.spender = spender;
            this.amount = amount;
        }

        public TransferInfo(Address spender, Uint256 amount) {
            super(spender, amount);
            this.spender = spender.getValue();
            this.amount = amount.getValue();
        }
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class DecreaseApproveEventResponse extends BaseEventResponse {
        public String _owner;

        public String _spender;

        public BigInteger _value;
    }

    public static class NextProductionChangeEventResponse extends BaseEventResponse {
        public BigInteger beforeValue;

        public BigInteger afterValue;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class PauseEventResponse extends BaseEventResponse {
    }

    public static class PoolAutoAddressChangeEventResponse extends BaseEventResponse {
        public String beforeValue;

        public String afterValue;
    }

    public static class PoolDistributeProportionChangeEventResponse extends BaseEventResponse {
        public BigInteger poolId;

        public BigInteger proportion;
    }

    public static class ProductionLimitChangeEventResponse extends BaseEventResponse {
        public BigInteger beforeValue;

        public BigInteger afterValue;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class UnpauseEventResponse extends BaseEventResponse {
    }
}
