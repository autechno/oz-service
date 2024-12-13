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
public class WalletManagerContract extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BALANCEOFTOKEN = "balanceOfToken";

    public static final String FUNC_COLLECT = "collect";

    public static final String FUNC_COLLECT2STORAGE = "collect2storage";

    public static final String FUNC_COLLECT2WITHDRAW = "collect2withdraw";

    public static final String FUNC_GENERATEUSERWALLET = "generateUserWallet";

    public static final String FUNC_GENERATEUSERWALLETBATCH = "generateUserWalletBatch";

    public static final String FUNC_GETCOLLECTWALLET = "getCollectWallet";

    public static final String FUNC_GETSUBUSERWALLETS = "getSubUserWallets";

    public static final String FUNC_GETTOKENCONTRACTS = "getTokenContracts";

    public static final String FUNC_GETUSERWALLETS = "getUserWallets";

    public static final String FUNC_GETUSERWALLETSNUMBERS = "getUserWalletsNumbers";

    public static final String FUNC_GETWITHDRAWWALLET = "getWithdrawWallet";

    public static final String FUNC_POPTOKENCONTRACTS = "popTokenContracts";

    public static final String FUNC_PUSHTOKENCONTRACTS = "pushTokenContracts";

    public static final String FUNC_REENABLEUSERWALLET = "reEnableUserWallet";

    public static final String FUNC_REGENERATECOLLECTWALLET = "reGenerateCollectWallet";

    public static final String FUNC_REGENERATEWITHDRAWWALLET = "reGenerateWithdrawWallet";

    public static final String FUNC_RECYCLEUSERWALLET = "recycleUserWallet";

    public static final String FUNC_RECYCLEUSERWALLETS = "recycleUserWallets";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WITHDRAWETH = "withdrawETH";

    public static final String FUNC_WITHDRAWETHBATCH = "withdrawETHBatch";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    public static final String FUNC_WITHDRAWTOKENBATCH = "withdrawTokenBatch";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected WalletManagerContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected WalletManagerContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected WalletManagerContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected WalletManagerContract(String contractAddress, Web3j web3j,
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

    public RemoteFunctionCall<List> balanceOf(String _wallet) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _wallet)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<TokenBalance>>() {}));
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

    public RemoteFunctionCall<BigInteger> balanceOfToken(String _wallet, String token) {
        final Function function = new Function(FUNC_BALANCEOFTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _wallet), 
                new org.web3j.abi.datatypes.Address(160, token)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> collect(List<CollectMeta> collectMatas) {
        final Function function = new Function(
                FUNC_COLLECT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<CollectMeta>(CollectMeta.class, collectMatas)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> collect2storage(String storageWallet,
            List<CollectMeta> collectMatas) {
        final Function function = new Function(
                FUNC_COLLECT2STORAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, storageWallet), 
                new org.web3j.abi.datatypes.DynamicArray<CollectMeta>(CollectMeta.class, collectMatas)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> collect2withdraw(List<CollectMeta> collectMatas) {
        final Function function = new Function(
                FUNC_COLLECT2WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<CollectMeta>(CollectMeta.class, collectMatas)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> generateUserWallet() {
        final Function function = new Function(
                FUNC_GENERATEUSERWALLET, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> generateUserWalletBatch(BigInteger count) {
        final Function function = new Function(
                FUNC_GENERATEUSERWALLETBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> getCollectWallet() {
        final Function function = new Function(FUNC_GETCOLLECTWALLET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getSubUserWallets(BigInteger _startIndexIncloud) {
        final Function function = new Function(FUNC_GETSUBUSERWALLETS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_startIndexIncloud)), 
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

    public RemoteFunctionCall<List> getTokenContracts() {
        final Function function = new Function(FUNC_GETTOKENCONTRACTS, 
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

    public RemoteFunctionCall<List> getUserWallets() {
        final Function function = new Function(FUNC_GETUSERWALLETS, 
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

    public RemoteFunctionCall<BigInteger> getUserWalletsNumbers() {
        final Function function = new Function(FUNC_GETUSERWALLETSNUMBERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getWithdrawWallet() {
        final Function function = new Function(FUNC_GETWITHDRAWWALLET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> popTokenContracts(String token) {
        final Function function = new Function(
                FUNC_POPTOKENCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> pushTokenContracts(String token) {
        final Function function = new Function(
                FUNC_PUSHTOKENCONTRACTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> reEnableUserWallet(List<String> _addrArr) {
        final Function function = new Function(
                FUNC_REENABLEUSERWALLET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_addrArr, org.web3j.abi.datatypes.Address.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> reGenerateCollectWallet() {
        final Function function = new Function(
                FUNC_REGENERATECOLLECTWALLET, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> reGenerateWithdrawWallet() {
        final Function function = new Function(
                FUNC_REGENERATEWITHDRAWWALLET, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> recycleUserWallet(String _addr) {
        final Function function = new Function(
                FUNC_RECYCLEUSERWALLET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _addr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> recycleUserWallets() {
        final Function function = new Function(
                FUNC_RECYCLEUSERWALLETS, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<TransactionReceipt> withdrawETH(String toAddr, BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWETH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, toAddr), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawETHBatch(List<WithdrawMeta> wdArr) {
        final Function function = new Function(
                FUNC_WITHDRAWETHBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<WithdrawMeta>(WithdrawMeta.class, wdArr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawToken(String token, String toAddr,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token), 
                new org.web3j.abi.datatypes.Address(160, toAddr), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawTokenBatch(String token,
            List<WithdrawMeta> wdArr) {
        final Function function = new Function(
                FUNC_WITHDRAWTOKENBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token), 
                new org.web3j.abi.datatypes.DynamicArray<WithdrawMeta>(WithdrawMeta.class, wdArr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static WalletManagerContract load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new WalletManagerContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static WalletManagerContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new WalletManagerContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static WalletManagerContract load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new WalletManagerContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static WalletManagerContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new WalletManagerContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class TokenBalance extends StaticStruct {
        public String token;

        public BigInteger balance;

        public TokenBalance(String token, BigInteger balance) {
            super(new org.web3j.abi.datatypes.Address(160, token), 
                    new org.web3j.abi.datatypes.generated.Uint256(balance));
            this.token = token;
            this.balance = balance;
        }

        public TokenBalance(Address token, Uint256 balance) {
            super(token, balance);
            this.token = token.getValue();
            this.balance = balance.getValue();
        }
    }

    public static class CollectMeta extends StaticStruct {
        public String token;

        public BigInteger limit;

        public CollectMeta(String token, BigInteger limit) {
            super(new org.web3j.abi.datatypes.Address(160, token), 
                    new org.web3j.abi.datatypes.generated.Uint256(limit));
            this.token = token;
            this.limit = limit;
        }

        public CollectMeta(Address token, Uint256 limit) {
            super(token, limit);
            this.token = token.getValue();
            this.limit = limit.getValue();
        }
    }

    public static class WithdrawMeta extends StaticStruct {
        public String toAddr;

        public BigInteger amount;

        public WithdrawMeta(String toAddr, BigInteger amount) {
            super(new org.web3j.abi.datatypes.Address(160, toAddr), 
                    new org.web3j.abi.datatypes.generated.Uint256(amount));
            this.toAddr = toAddr;
            this.amount = amount;
        }

        public WithdrawMeta(Address toAddr, Uint256 amount) {
            super(toAddr, amount);
            this.toAddr = toAddr.getValue();
            this.amount = amount.getValue();
        }
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
