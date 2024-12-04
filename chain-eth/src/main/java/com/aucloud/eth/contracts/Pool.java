package com.aucloud.eth.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class Pool extends Contract {
    public static final String BINARY = "6080604052348015600e575f80fd5b50604051610243380380610243833981016040819052602b916043565b600180546001600160a01b031916331790555f556059565b5f602082840312156052575f80fd5b5051919050565b6101dd806100665f395ff3fe608060405234801561000f575f80fd5b5060043610610034575f3560e01c80633e0dc34e14610038578063d9caed1214610052575b5f80fd5b6100405f5481565b60405190815260200160405180910390f35b610065610060366004610148565b610067565b005b60015433906001600160a01b031681146100b65760405162461bcd60e51b815260206004820152600c60248201526b2737ba1036bc9037bbb732b960a11b604482015260640160405180910390fd5b60405163a9059cbb60e01b81526001600160a01b0384811660048301526024820184905285169063a9059cbb906044016020604051808303815f875af1158015610102573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906101269190610181565b5050505050565b80356001600160a01b0381168114610143575f80fd5b919050565b5f805f6060848603121561015a575f80fd5b6101638461012d565b92506101716020850161012d565b9150604084013590509250925092565b5f60208284031215610191575f80fd5b815180151581146101a0575f80fd5b939250505056fea2646970667358221220e7b1eb2f8209a3c0749a2dceba2e4f738cd77197a6c1cc989822cdb16656f47d64736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_POOLID = "poolId";

    public static final String FUNC_WITHDRAW = "withdraw";

    @Deprecated
    protected Pool(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Pool(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Pool(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Pool(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> poolId() {
        final Function function = new Function(FUNC_POOLID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String contractAddress, String spender,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contractAddress), 
                new org.web3j.abi.datatypes.Address(160, spender), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Pool load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new Pool(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Pool load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Pool(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Pool load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new Pool(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Pool load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Pool(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Pool> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, BigInteger id) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)));
        return deployRemoteCall(Pool.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<Pool> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, BigInteger id) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)));
        return deployRemoteCall(Pool.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Pool> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit, BigInteger id) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)));
        return deployRemoteCall(Pool.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Pool> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, BigInteger id) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)));
        return deployRemoteCall(Pool.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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
}
