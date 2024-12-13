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
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
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
    public static final String BINARY = "Bin file was not provided";

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
