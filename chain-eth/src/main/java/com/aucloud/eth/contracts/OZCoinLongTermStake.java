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
import org.web3j.abi.datatypes.DynamicArray;
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
public class OZCoinLongTermStake extends Contract {
    public static final String BINARY = "60806040526305a39a80600155348015610017575f80fd5b5060405161153a38038061153a83398101604081905261003691610111565b338061005b57604051631e4fbdf760e01b81525f600482015260240160405180910390fd5b610064816100a7565b50600880546001600160a01b039485166001600160a01b031991821617909155600780549385169382169390931790925560098054919093169116179055610151565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b80516001600160a01b038116811461010c575f80fd5b919050565b5f805f60608486031215610123575f80fd5b61012c846100f6565b925061013a602085016100f6565b9150610148604085016100f6565b90509250925092565b6113dc8061015e5f395ff3fe608060405234801561000f575f80fd5b50600436106100e5575f3560e01c806396e9446811610088578063acebba9911610063578063acebba99146101e9578063b604a19414610200578063b8e85fec14610209578063f2fde38b14610229575f80fd5b806396e94468146101795780639abe7a86146101c3578063a34d42b8146101d6575f80fd5b80631e2eb088116100c35780631e2eb0881461012e5780635467e0a714610159578063715018a6146101615780638da5cb5b14610169575f80fd5b806301e33667146100e95780630abe3d1a146100fe57806311da60b414610126575b5f80fd5b6100fc6100f73660046110d8565b61023c565b005b61011161010c366004611112565b6102fd565b60405190151581526020015b60405180910390f35b6100fc610454565b61014161013c366004611178565b6109dd565b6040516001600160a01b03909116815260200161011d565b610111610a05565b6100fc610bf3565b5f546001600160a01b0316610141565b61018c61018736600461118f565b610c04565b604080516001600160a01b0390971687526020870195909552938501929092526060840152608083015260a082015260c00161011d565b61018c6101d136600461118f565b610c5e565b6101116101e43660046111b7565b610c77565b6101f260065481565b60405190815260200161011d565b6101f260025481565b61021c6102173660046111b7565b610cd1565b60405161011d91906111d7565b6100fc6102373660046111b7565b610dbf565b6008546001600160a01b031633146102875760405162461bcd60e51b81526020600482015260096024820152682337b93134b23232b760b91b60448201526064015b60405180910390fd5b60405163a9059cbb60e01b81526001600160a01b0383811660048301526024820183905284169063a9059cbb906044016020604051808303815f875af11580156102d3573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906102f79190611259565b50505050565b6040805160a0810182526001600160a01b03898116825230602083018181528385018b8152606085018b8152608086018b81526007549751637dcc7fd560e01b81528751871660048201529351861660248501529151604484015251606483015251608482015260ff881660a482015260c4810187905260e481018690525f949193929190911690637dcc7fd590610104015f604051808303815f87803b1580156103a6575f80fd5b505af11580156103b8573d5f803e3d5ffd5b50506007546040516323b872dd60e01b81526001600160a01b038e811660048301528681166024830152604482018e905290911692506323b872dd91506064016020604051808303815f875af1158015610414573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906104389190611259565b506104438a8a610dfc565b5060019a9950505050505050505050565b61045c610fdb565b425f61046782611007565b905060065481116104ab5760405162461bcd60e51b815260206004820152600e60248201526d496e2074686520636f6f6c696e6760901b604482015260640161027e565b6002545f0361052a5760095460405163a193be4d60e01b81526001600160a01b039091169063a193be4d906104e590600290600401611298565b6020604051808303815f875af1158015610501573d5f803e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105259190611259565b505050565b600954604051632596038360e11b8152600481018490525f916001600160a01b031690634b2c070690602401602060405180830381865afa158015610571573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061059591906112a6565b6009549091505f906001600160a01b031663be96a8bd6105b66001856112d1565b60026040518363ffffffff1660e01b81526004016105d59291906112e4565b602060405180830381865afa1580156105f0573d5f803e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061061491906112a6565b6004549091505f6106266001836112d1565b90505f60035f6004848154811061063f5761063f6112f8565b5f9182526020808320909101546001600160a01b0316835282019290925260400181208054909250906106736001836112d1565b90505b5f838281548110610689576106896112f8565b905f5260205f20906006020190505f816001015490505f612710600254836127106106b4919061130c565b6106be9190611323565b6106c8908b61130c565b6106d29190611323565b905080836005015f8282546106e79190611342565b909155505060048301548c106109c5576040805160c08101825284546001600160a01b03168152600185015460208201526002850154918101919091526003840154606082015260048401546080820152600584015460a08201526107c69080516001600160a01b039081165f9081526005602081815260408084208054600181810183559186529483902087516006909602018054959096166001600160a01b03199095169490941785559085015192840192909255908301516002830155606083015160038301556080830151600483015560a090920151910155565b826001015460025f8282546107db91906112d1565b9091555050855486906107f0906001906112d1565b81548110610800576108006112f8565b905f5260205f20906006020186858154811061081e5761081e6112f8565b5f9182526020909120825460069092020180546001600160a01b0319166001600160a01b0390921691909117815560018083015490820155600280830154908201556003808301549082015560048083015490820155600591820154910155855486908061088e5761088e611355565b5f8281526020812060065f199093019283020180546001600160a01b0319168155600181018290556002810182905560038101829055600481018290556005018190559155865490036109c55760035f600489815481106108f1576108f16112f8565b5f9182526020808320909101546001600160a01b03168352820192909252604001812061091d91611064565b6004805461092d906001906112d1565b8154811061093d5761093d6112f8565b5f91825260209091200154600480546001600160a01b039092169189908110610968576109686112f8565b905f5260205f20015f6101000a8154816001600160a01b0302191690836001600160a01b0316021790555060048054806109a4576109a4611355565b5f8281526020902081015f1990810180546001600160a01b03191690550190555b50505080806109d390611369565b915050610676565b565b600481815481106109ec575f80fd5b5f918252602090912001546001600160a01b0316905081565b335f8181526005602052604081208054919291610a5b5760405162461bcd60e51b81526020600482015260146024820152732737b73290333932b2b237b69039ba30b5b2b99760611b604482015260640161027e565b5f5b8154811015610bc9575f828281548110610a7957610a796112f8565b5f9182526020918290206040805160c081018252600690930290910180546001600160a01b039081168452600182015494840185905260028201548484015260038201546060850152600480830154608086015260059092015460a0850152600754925163a9059cbb60e01b8152939550919091169263a9059cbb92610b1692899291016001600160a01b03929092168252602082015260400190565b6020604051808303815f875af1158015610b32573d5f803e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610b569190611259565b5060095460a082015160405163639c668b60e01b81526001600160a01b039092169163639c668b91610b8f91600291899160040161137e565b5f604051808303815f87803b158015610ba6575f80fd5b505af1158015610bb8573d5f803e3d5ffd5b505060019093019250610a5d915050565b506001600160a01b0382165f908152600560205260408120610bea91611064565b60019250505090565b610bfb610fdb565b6109db5f611015565b6005602052815f5260405f208181548110610c1d575f80fd5b5f9182526020909120600690910201805460018201546002830154600384015460048501546005909501546001600160a01b03909416965091945092909186565b6003602052815f5260405f208181548110610c1d575f80fd5b6008545f906001600160a01b03163314610cbf5760405162461bcd60e51b81526020600482015260096024820152682337b93134b23232b760b91b604482015260640161027e565b610cc882611015565b5060015b919050565b6001600160a01b0381165f908152600360209081526040808320805482518185028101850190935280835260609493849084015b82821015610d75575f8481526020908190206040805160c0810182526006860290920180546001600160a01b031683526001808201548486015260028201549284019290925260038101546060840152600481015460808401526005015460a08301529083529092019101610d05565b5050505090505f815111610db95760405162461bcd60e51b815260206004820152600b60248201526a139bdb995e1a5cdd195b9d60aa1b604482015260640161027e565b92915050565b610dc7610fdb565b6001600160a01b038116610df057604051631e4fbdf760e01b81525f600482015260240161027e565b610df981611015565b50565b5f80610e0b6201518042611323565b90505f60015482610e1c9190611342565b6001600160a01b0386165f90815260036020908152604080832080548251818502810185019093528083529495509293909291849084015b82821015610ec4575f8481526020908190206040805160c0810182526006860290920180546001600160a01b031683526001808201548486015260028201549284019290925260038101546060840152600481015460808401526005015460a08301529083529092019101610e54565b50505050905080515f03610f1d57600480546001810182555f919091527f8a35acfbc15ff81a39ae7d344fd709f28e8600b4aa8c65c6b64bfe7fe36bd19b0180546001600160a01b0319166001600160a01b0388161790555b6001600160a01b038681165f818152600360208181526040808420815160c0810183529586528583018c81529186018a81526001805460608901908152608089018c815260a08a0189815285548085018755958a52969098209851600690940290980180546001600160a01b0319169390991692909217885591519087015551600280870191909155935191850191909155905160048401555160059092019190915554610fcc908690611342565b60025550600195945050505050565b5f546001600160a01b031633146109db5760405163118cdaa760e01b815233600482015260240161027e565b5f610db96201518083611323565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b5080545f8255600602905f5260205f2090810190610df991905b808211156110be5780546001600160a01b03191681555f60018201819055600282018190556003820181905560048201819055600582015560060161107e565b5090565b80356001600160a01b0381168114610ccc575f80fd5b5f805f606084860312156110ea575f80fd5b6110f3846110c2565b9250611101602085016110c2565b929592945050506040919091013590565b5f805f805f805f60e0888a031215611128575f80fd5b611131886110c2565b9650602088013595506040880135945060608801359350608088013560ff8116811461115b575f80fd5b9699959850939692959460a0840135945060c09093013592915050565b5f60208284031215611188575f80fd5b5035919050565b5f80604083850312156111a0575f80fd5b6111a9836110c2565b946020939093013593505050565b5f602082840312156111c7575f80fd5b6111d0826110c2565b9392505050565b602080825282518282018190525f918401906040840190835b8181101561124e57835180516001600160a01b031684526020808201518186015260408083015190860152606080830151908601526080808301519086015260a091820151918501919091529093019260c0909201916001016111f0565b509095945050505050565b5f60208284031215611269575f80fd5b815180151581146111d0575f80fd5b6007811061129457634e487b7160e01b5f52602160045260245ffd5b9052565b60208101610db98284611278565b5f602082840312156112b6575f80fd5b5051919050565b634e487b7160e01b5f52601160045260245ffd5b81810381811115610db957610db96112bd565b828152604081016111d06020830184611278565b634e487b7160e01b5f52603260045260245ffd5b8082028115828204841417610db957610db96112bd565b5f8261133d57634e487b7160e01b5f52601260045260245ffd5b500490565b80820180821115610db957610db96112bd565b634e487b7160e01b5f52603160045260245ffd5b5f81611377576113776112bd565b505f190190565b6060810161138c8286611278565b6001600160a01b039390931660208201526040015291905056fea264697066735822122007ae472a919646f8c1968fbf5b86f353355e061944a8840fd84160c05186bf6364736f6c634300081a0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ACCOUNTKEYS = "accountKeys";

    public static final String FUNC_FREEDOMACCOUNTS = "freedomAccounts";

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

    public static final Event ACCOUNTSTAKEEXPIRATIONTIMESTAMPCHANGE_EVENT = new Event("AccountStakeExpirationTimestampChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected OZCoinLongTermStake(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OZCoinLongTermStake(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OZCoinLongTermStake(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OZCoinLongTermStake(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<AccountStakeExpirationTimestampChangeEventResponse> getAccountStakeExpirationTimestampChangeEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACCOUNTSTAKEEXPIRATIONTIMESTAMPCHANGE_EVENT, transactionReceipt);
        ArrayList<AccountStakeExpirationTimestampChangeEventResponse> responses = new ArrayList<AccountStakeExpirationTimestampChangeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AccountStakeExpirationTimestampChangeEventResponse typedResponse = new AccountStakeExpirationTimestampChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.serialNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.beforeValue = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.afterValue = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AccountStakeExpirationTimestampChangeEventResponse getAccountStakeExpirationTimestampChangeEventFromLog(
            Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACCOUNTSTAKEEXPIRATIONTIMESTAMPCHANGE_EVENT, log);
        AccountStakeExpirationTimestampChangeEventResponse typedResponse = new AccountStakeExpirationTimestampChangeEventResponse();
        typedResponse.log = log;
        typedResponse.accountAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.serialNumber = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.beforeValue = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.afterValue = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
        return typedResponse;
    }

    public Flowable<AccountStakeExpirationTimestampChangeEventResponse> accountStakeExpirationTimestampChangeEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAccountStakeExpirationTimestampChangeEventFromLog(log));
    }

    public Flowable<AccountStakeExpirationTimestampChangeEventResponse> accountStakeExpirationTimestampChangeEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCOUNTSTAKEEXPIRATIONTIMESTAMPCHANGE_EVENT));
        return accountStakeExpirationTimestampChangeEventFlowable(filter);
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

    public RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> freedomAccounts(
            String param0, BigInteger param1) {
        final Function function = new Function(FUNC_FREEDOMACCOUNTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
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

    public RemoteFunctionCall<List> getAccountByAddress(String accountAddress) {
        final Function function = new Function(FUNC_GETACCOUNTBYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, accountAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<StakeRecord>>() {}));
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

    public RemoteFunctionCall<TransactionReceipt> redemption() {
        final Function function = new Function(
                FUNC_REDEMPTION, 
                Arrays.<Type>asList(), 
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

    public RemoteFunctionCall<TransactionReceipt> settle() {
        final Function function = new Function(
                FUNC_SETTLE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> stake(String from, BigInteger amount,
            BigInteger nonce, BigInteger deadline, BigInteger v, byte[] r, byte[] s) {
        final Function function = new Function(
                FUNC_STAKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                new org.web3j.abi.datatypes.generated.Uint256(deadline), 
                new org.web3j.abi.datatypes.generated.Uint8(v), 
                new org.web3j.abi.datatypes.generated.Bytes32(r), 
                new org.web3j.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple6<String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> stakeAccounts(
            String param0, BigInteger param1) {
        final Function function = new Function(FUNC_STAKEACCOUNTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
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
    public static OZCoinLongTermStake load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OZCoinLongTermStake(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OZCoinLongTermStake load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OZCoinLongTermStake(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OZCoinLongTermStake load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OZCoinLongTermStake(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OZCoinLongTermStake load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OZCoinLongTermStake(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OZCoinLongTermStake> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String multiSignWalletAddress,
            String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinLongTermStake.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<OZCoinLongTermStake> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String multiSignWalletAddress, String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinLongTermStake.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OZCoinLongTermStake> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String multiSignWalletAddress,
            String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinLongTermStake.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<OZCoinLongTermStake> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String multiSignWalletAddress, String ozcContractAddress, String totoContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, multiSignWalletAddress), 
                new org.web3j.abi.datatypes.Address(160, ozcContractAddress), 
                new org.web3j.abi.datatypes.Address(160, totoContractAddress)));
        return deployRemoteCall(OZCoinLongTermStake.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
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

    public static class StakeRecord extends StaticStruct {
        public String accountAddress;

        public BigInteger ozcStakeAmount;

        public BigInteger stakeTime;

        public BigInteger lockInPeriod;

        public BigInteger expectedTime;

        public BigInteger totoIncomeAmount;

        public StakeRecord(String accountAddress, BigInteger ozcStakeAmount, BigInteger stakeTime,
                BigInteger lockInPeriod, BigInteger expectedTime, BigInteger totoIncomeAmount) {
            super(new org.web3j.abi.datatypes.Address(160, accountAddress), 
                    new org.web3j.abi.datatypes.generated.Uint256(ozcStakeAmount), 
                    new org.web3j.abi.datatypes.generated.Uint256(stakeTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(lockInPeriod), 
                    new org.web3j.abi.datatypes.generated.Uint256(expectedTime), 
                    new org.web3j.abi.datatypes.generated.Uint256(totoIncomeAmount));
            this.accountAddress = accountAddress;
            this.ozcStakeAmount = ozcStakeAmount;
            this.stakeTime = stakeTime;
            this.lockInPeriod = lockInPeriod;
            this.expectedTime = expectedTime;
            this.totoIncomeAmount = totoIncomeAmount;
        }

        public StakeRecord(Address accountAddress, Uint256 ozcStakeAmount, Uint256 stakeTime,
                Uint256 lockInPeriod, Uint256 expectedTime, Uint256 totoIncomeAmount) {
            super(accountAddress, ozcStakeAmount, stakeTime, lockInPeriod, expectedTime, totoIncomeAmount);
            this.accountAddress = accountAddress.getValue();
            this.ozcStakeAmount = ozcStakeAmount.getValue();
            this.stakeTime = stakeTime.getValue();
            this.lockInPeriod = lockInPeriod.getValue();
            this.expectedTime = expectedTime.getValue();
            this.totoIncomeAmount = totoIncomeAmount.getValue();
        }
    }

    public static class AccountStakeExpirationTimestampChangeEventResponse extends BaseEventResponse {
        public String accountAddress;

        public BigInteger serialNumber;

        public BigInteger beforeValue;

        public BigInteger afterValue;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
