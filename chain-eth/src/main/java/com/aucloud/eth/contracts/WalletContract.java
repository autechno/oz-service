package com.aucloud.eth.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class WalletContract extends Contract {
    public static final String BINARY = "60806040525f805460ff60a01b19169055348015601a575f80fd5b506040516105be3803806105be833981016040819052603791605a565b5f80546001600160a01b0319166001600160a01b03929092169190911790556085565b5f602082840312156069575f80fd5b81516001600160a01b0381168114607e575f80fd5b9392505050565b61052c806100925f395ff3fe608060405260043610610057575f3560e01c806301e33667146100625780635c975abb146100835780637382f13b146100b757806383197ef0146100cb5780638da5cb5b146100df578063f3fef3a314610115575f80fd5b3661005e57005b5f80fd5b34801561006d575f80fd5b5061008161007c366004610444565b610134565b005b34801561008e575f80fd5b505f546100a290600160a01b900460ff1681565b60405190151581526020015b60405180910390f35b3480156100c2575f80fd5b506100816102bd565b3480156100d6575f80fd5b506100816102f4565b3480156100ea575f80fd5b505f546100fd906001600160a01b031681565b6040516001600160a01b0390911681526020016100ae565b348015610120575f80fd5b5061008161012f366004610482565b610331565b5f546001600160a01b031633146101665760405162461bcd60e51b815260040161015d906104ac565b60405180910390fd5b5f54600160a01b900460ff16156101bf5760405162461bcd60e51b815260206004820152601c60248201527f4d757374206265207573656420776974686f75742070617573696e6700000000604482015260640161015d565b7319c0947b0e1169c00854caf563e20d742c462ee5196001600160a01b038416016102465760405163a9059cbb60e01b81526001600160a01b0383811660048301526024820183905284169063a9059cbb906044015f604051808303815f87803b15801561022b575f80fd5b505af115801561023d573d5f803e3d5ffd5b50505050505050565b60405163a9059cbb60e01b81526001600160a01b0383811660048301526024820183905284169063a9059cbb906044016020604051808303815f875af1158015610292573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102b691906104d0565b505b505050565b5f546001600160a01b031633146102e65760405162461bcd60e51b815260040161015d906104ac565b5f805460ff60a01b19169055565b5f546001600160a01b0316331461031d5760405162461bcd60e51b815260040161015d906104ac565b5f805460ff60a01b1916600160a01b179055565b5f546001600160a01b0316331461035a5760405162461bcd60e51b815260040161015d906104ac565b5f54600160a01b900460ff16156103b35760405162461bcd60e51b815260206004820152601c60248201527f4d757374206265207573656420776974686f75742070617573696e6700000000604482015260640161015d565b804710156103fa5760405162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b604482015260640161015d565b6040516001600160a01b0383169082156108fc029083905f818181858888f193505050501580156102b8573d5f803e3d5ffd5b6001600160a01b0381168114610441575f80fd5b50565b5f805f60608486031215610456575f80fd5b83356104618161042d565b925060208401356104718161042d565b929592945050506040919091013590565b5f8060408385031215610493575f80fd5b823561049e8161042d565b946020939093013593505050565b6020808252600a90820152696e6f74206f776e65722160b01b604082015260600190565b5f602082840312156104e0575f80fd5b815180151581146104ef575f80fd5b939250505056fea2646970667358221220f59a889f680c8282767895f6fec4c47a300fa748435c1ac3b0b6d375a7e531e364736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_DESTROY = "destroy";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PAUSED = "paused";

    public static final String FUNC_REENABLE = "reEnable";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_WITHDRAWTOKEN = "withdrawToken";

    @Deprecated
    protected WalletContract(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected WalletContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected WalletContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected WalletContract(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> destroy() {
        final Function function = new Function(
                FUNC_DESTROY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> paused() {
        final Function function = new Function(FUNC_PAUSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> reEnable() {
        final Function function = new Function(
                FUNC_REENABLE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(String recipient, BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawToken(String token, String to,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAWTOKEN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static WalletContract load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new WalletContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static WalletContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new WalletContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static WalletContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new WalletContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static WalletContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new WalletContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<WalletContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(WalletContract.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<WalletContract> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(WalletContract.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<WalletContract> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(WalletContract.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<WalletContract> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(WalletContract.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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
