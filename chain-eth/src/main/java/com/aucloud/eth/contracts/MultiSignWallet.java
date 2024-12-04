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
public class MultiSignWallet extends Contract {
    public static final String BINARY = "5f80556001608081815260c06040529060a06020803683375050815161002c926001925060200190610af2565b5060408051600180825281830190925290602080830190803683375050815161005c926002925060200190610af2565b50348015610068575f80fd5b507fa37023a1370229cd064445d354a12ad69457b1e77dfe2c1030da876caee8d8f88054600260ff19918216811790925560086020527fc51f03c2d4a0792e778b80d9e26ea457d207ebb8c4f30e2f731753c9bb9f374380548216831790557f26d9906b59f531239207937dd62a7925be18e2b6e2b47567415efb5199ed319980548216831790557f13eff3fcb872a371bd08264a2adb95f263de8fbf8246144924e37fb65e10106980548216831790557f52c80e3a7939eb3f8188fe6e951052df9ac6970b8ae5cea9f50a087613e5d4ad8054821660039081179091557f3dd9eb19f54bc0b914dc0dfb064774480bae83cb02c67db96831c2fef58f20dc80548316841790557fac6047d695d1ffbcd6b00d2527d13aa68b10fc01cb8c5bad91ea41d20fb31a2480548316841790557f5aac885ae0d753b082058aec207b4019ce169f2dfb01e36dcac056756303034780548316841790557fd6e4be347d6ff40203f564261b473682b2cb95b777e2f917435eca03c330d87180548316821790557fc667789541689eb6249b2eb8678f863f38f5b51c4a1786615d2543925bd0e35c80548316841790557f92781a64759860e6ef8cf61cdc60caad12482a3f634da991b5b4e22c2306b47a80548316841790557f5240c1b95e63b5823457971caebf670658923ab85161c65823354cd0e20fae6e80548316841790557f7b24e0f8c796a0106e93f72a5d9acaee2a2cae6b9042fc91e42e4aef96d26a5080548316841790557f2d11cf0bd1e26a4f2a9f482fc4fabbb2154b933c00480455d8a1643b5171033680548316841790557f31eb86e36b70e189babb09ee91e52bc88160b1405799ee87841ddf081a3a8fb980548316841790557f78e36778d556c0578a0e96c3aa7a32457aca71866501b7d3acf5a0a1df6366b680548316841790557fee2cfd4db9f5f2bb96941972e8f6b1754ce4bc9c19ea6062d72d2d02c58ff77b80548316841790557f466b6d3ad389814ef0b3252a6e7fb74470adb0a6a1ce08ef9492501ab211431780548316841790557f1264b42136a9d99b82b61900bc9437422b6c7702eef611a40fcfd769fc01f16980548316841790557f741c5de5d0003b156aab2624e36fa55e9cbde37237fd3ac4a9b69422efa3597280548316841790557f119c5578c965672cdafa8f3b9730241f43805eef01eebd2d3e11ade166749c9b80548316841790557fe25e2f5fabf6eb90c26a84cd883830e2bcccfc1c7bec3834c05d8c06f21977eb80548316841790557fbde23a4301d3b0ee087da0f403af7ae048eaa30ce8c1dcbe2c96b8e7bb64d56e80548316841790557fa7f4e35329fc6b06124ccaa01943d02c95b65276110a463ad075c967ee575c4f8054831660019081179091557f1ef35f43df9b86a731894fd0a3df30006a8ebe8304e8ee3339e6cf7a2057dcb880548416851790557f9f8ac39fa3ed388e18e62b907852a16c50e5869c95991486fabc30b30552bd5580548416851790557f269d5792057e69b28324c5396795ea1476c23a24ab96241d76ad1ad8b5eb21dd80548416851790557f3caa5a4092bd81fda8fd87f2e5824e6b1777cc4f841e7b8bebd44b66159a745180548416851790557f163e15265c0070d3efee8a54f601dd5bf95b0a4e57c566a1e9c359673a0c39ce80548416851790557f733ffc8dcaef28de078ad2da2827580243f79d82af992894ed89f300eaa6e47c80548416851790557facaf7ac81b17457228ac71af132a7e3f8c8ecdbd9e84c09876616bfe0c6df79b80548416851790557f900f20c1c6384029df431f740cd96cf18ea02d4039ea423b87e24379a68bddef80548416851790557fdf26ca22f2542f361eebc480c9cabb9dfbbdd5244afb981464b17d799dcbd11b80548416851790557fdc908c3bae7f149e945d615d8c4769cc917ba90dde21f711d060d7d462cdaeda80548416851790557f1dc576fcaaa6d0c43a5d5edbfeb09e0ad22ac5aefc9c7f34ab2d09c087f872b680548416831790557f49845d9413033061d20155fbdfcc587bf21c51af76645b83d7c6d74a9250e50580548416851790557f75431ab4e1e59a7137882fd9aedce83d8bba01f750115ce96b54e586a121e97a80548416831790557f93e43d0e4e8cafef4510afc9cf9cabd903f845c5d688df7156095ef12345ed0f80548416851790557f4d2cc1cefa87f180248b9cef4c9a1b37f053e89767fbccace92c7b1baee5309880548416851790557f1981412d9849993e875c80cacc5057b497054aa883d5633bb62993745cbc3fa980548416821790557f0f060d32ab9d88c61c1c9f7342cc390dc9a83e9352198b5098225b206c32ed6480548416851790557f39f712e27d4ceef47696059fdf86367429e614561f0d826cb0bc05dedfa8fed580548416821790557f05be858a41b1bc196e7a73241c2dccdb812ea473ce67f8766c79c1848b42661580548416831790557f6f3946173335c1d18e23555c35e7b2d712775d91647c909c23b3ce9ebd7bfcf780548416821790557f1c4358aa8672bce279a3616413164ca04b407be8bd5139192985e5a62200203d805484169092179091557f57102275cb065999b4d04f340aa5fcc112c1e3200118b866fcae1f61c2d5c95d8054831690911790557f8badc4ea77db0eee3a1f867424d284745a7ffba167aedf34af2c78bb61ba30b480548216831790557fcba7f72efe8ae60480f744c8700dedf56211a68d2a5f8223f8f061b8b336791c80548216831790557f280a0749d0eced2d2e4a9b93aa4640761e7d24653da9833ae99cf3bd1e7f83b880548216831790557fa6794c3dc3b09a8250c6d99983d3137d8287948fd26d6c2eca8bd93ef4e8c42e8054821683179055633ba4f67160e21b5f527f5f4f5f77809c2dd4cc20e9ef54cb3c24acbf97cf9fc1b0f73508dca3bb8c05b580548216831790557f31a8f4eb801a5c0f503f9737f3f994c213918d643e1a8cf0a51f051b4b832b65805490911690911790556108fd73e9ee90f76119b01f45fe5343da588bd33a51030261097b565b5061091b73b05bd7ab36421ce07d91d0b85dfa3c83c3c1557361097b565b50610939738c5a3b3822d3383cc7fcb61226357a87a6b124fc610a4f565b5061095773fd6201054a5e8d02f3602c8dbea626a3a88c4a5a610a4f565b5061097573f97bbf2179fcb382cc08cbf6ce6e8ce683a1e67f610a4f565b50610b69565b6001545f90600310156109d55760405162461bcd60e51b815260206004820152601a60248201527f4e756d626572206f6620537570657241646d696e204c696d697400000000000060448201526064015b60405180910390fd5b6001805480820182555f8290527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b0385161790555b6001600160a01b03929092165f908152600360205260409020805460ff191660ff9093169290921790915550600190565b6002545f9060041015610aa45760405162461bcd60e51b815260206004820152601560248201527f4e756d626572206f662041646d696e204c696d6974000000000000000000000060448201526064016109cc565b600280546001810182555f8290527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace0180546001600160a01b0319166001600160a01b038516179055610a1e565b828054828255905f5260205f20908101928215610b45579160200282015b82811115610b4557825182546001600160a01b0319166001600160a01b03909116178255602090920191600190910190610b10565b50610b51929150610b55565b5090565b5b80821115610b51575f8155600101610b56565b61170f80610b765f395ff3fe608060405234801561000f575f80fd5b5060043610610106575f3560e01c8063569090a91161009e578063a7e475981161006e578063a7e475981461027d578063b07224c7146102a9578063b3292ff0146102bc578063bbf1b2f1146102cf578063c01a8c84146102e2575f80fd5b8063569090a91461020c578063704802751461022b5780638916d12c1461023e578063991a3a4d1461025e575f80fd5b806337593551116100d957806337593551146101a85780633f3270ff146101bd5780634902e4aa146101c5578063563589ab146101d8575f80fd5b80630e0b0bd51461010a57806314bfd6d0146101475780631785f53c146101725780632cffb8b414610195575b5f80fd5b6101346101183660046111ca565b600460209081525f928352604080842090915290825290205481565b6040519081526020015b60405180910390f35b61015a6101553660046111f4565b6102f7565b6040516001600160a01b03909116815260200161013e565b61018561018036600461120b565b61031f565b604051901515815260200161013e565b61015a6101a33660046111f4565b6104bd565b6101b06104cc565b60405161013e9190611224565b6101b061052c565b6101856101d336600461120b565b61058a565b6101fa6101e636600461120b565b60036020525f908152604090205460ff1681565b60405160ff909116815260200161013e565b61013461021a3660046111f4565b60066020525f908152604090205481565b61018561023936600461120b565b6106d7565b61025161024c3660046111f4565b610709565b60405161013e919061126f565b61013461026c3660046111f4565b60056020525f908152604090205481565b61029061028b366004611380565b610819565b6040516001600160e01b0319909116815260200161013e565b6101856102b73660046113ca565b61098a565b6101856102ca36600461120b565b6109f1565b6101346102dd366004611418565b610a20565b6102f56102f03660046111f4565b610bbe565b005b60028181548110610306575f80fd5b5f918252602090912001546001600160a01b0316905081565b5f3330146103485760405162461bcd60e51b815260040161033f90611463565b60405180910390fd5b60015460025460049161035a9161149a565b1161039f5760405162461bcd60e51b8152602060048201526015602482015274139d5b58995c881bd98810591b5a5b88131a5b5a5d605a1b604482015260640161033f565b6002545f5b8181101561049357836001600160a01b0316600282815481106103c9576103c96114ad565b5f918252602090912001546001600160a01b03160361048b5760026103ef6001846114c1565b815481106103ff576103ff6114ad565b5f91825260209091200154600280546001600160a01b03909216918390811061042a5761042a6114ad565b905f5260205f20015f6101000a8154816001600160a01b0302191690836001600160a01b031602179055506002805480610466576104666114d4565b5f8281526020902081015f1990810180546001600160a01b0319169055019055610493565b6001016103a4565b5050506001600160a01b0381165f908152600360205260409020805460ff1916905560015b919050565b60018181548110610306575f80fd5b6060600180548060200260200160405190810160405280929190818152602001828054801561052257602002820191905f5260205f20905b81546001600160a01b03168152600190910190602001808311610504575b5050505050905090565b6060600280548060200260200160405190810160405280929190818152602001828054801561052257602002820191905f5260205f209081546001600160a01b03168152600190910190602001808311610504575050505050905090565b5f3330146105aa5760405162461bcd60e51b815260040161033f90611463565b6001546002546004916105bc9161149a565b116106095760405162461bcd60e51b815260206004820152601a60248201527f4e756d626572206f6620537570657241646d696e204c696d6974000000000000604482015260640161033f565b6001545f5b8181101561049357836001600160a01b031660018281548110610633576106336114ad565b5f918252602090912001546001600160a01b0316036106cf57600161065881846114c1565b81548110610668576106686114ad565b5f91825260209091200154600180546001600160a01b039092169183908110610693576106936114ad565b905f5260205f20015f6101000a8154816001600160a01b0302191690836001600160a01b031602179055506001805480610466576104666114d4565b60010161060e565b5f3330146106f75760405162461bcd60e51b815260040161033f90611463565b61070082610ebe565b50600192915050565b604080516080810182525f808252606060208301819052928201819052918101919091525f8281526009602090815260409182902082516080810190935280546001600160a01b031683526001810180549192840191610768906114e8565b80601f0160208091040260200160405190810160405280929190818152602001828054610794906114e8565b80156107df5780601f106107b6576101008083540402835291602001916107df565b820191905f5260205f20905b8154815290600101906020018083116107c257829003601f168201915b50505091835250506002919091015460e081901b6001600160e01b0319166020830152640100000000900460ff1660409091015292915050565b5f80601883600381518110610830576108306114ad565b016020015184516001600160f81b031990911690911c906010908590600290811061085d5761085d6114ad565b016020015185516001600160f81b031990911690911c906008908690600190811061088a5761088a6114ad565b016020015186516001600160f81b031990911690911c9086905f906108b1576108b16114ad565b602001015160f81c60f81b6001600160f81b03191617171790505f835167ffffffffffffffff8111156108e6576108e66112e1565b6040519080825280601f01601f191660200182016040528015610910576020820181803683370190505b5090505f5b6004855161092391906114c1565b811015610981578461093682600461149a565b81518110610946576109466114ad565b602001015160f81c60f81b828281518110610963576109636114ad565b60200101906001600160f81b03191690815f1a905350600101610915565b50909392505050565b5f3330146109aa5760405162461bcd60e51b815260040161033f90611463565b506001600160e01b0319929092165f908152600760209081526040808320805460ff95861660ff199182161790915560089092529091208054929093169116179055600190565b5f333014610a115760405162461bcd60e51b815260040161033f90611463565b610a1a82610f86565b92915050565b5f80610a2b83610819565b6001600160e01b031981165f9081526008602090815260408083205460079092528220549293509091610a649160ff9081169116611520565b60ff1611610aa55760405162461bcd60e51b815260206004820152600e60248201526d115e18d95c1d1a5bdb8818d85b1b60921b604482015260640161033f565b5f6040518060800160405280866001600160a01b03168152602001858152602001836001600160e01b03191681526020015f6001811115610ae857610ae8611539565b60ff1690525f54909150610afd816001611028565b5f908155818152600960209081526040909120835181546001600160a01b0319166001600160a01b03909116178155908301518391906001820190610b429082611599565b5060408201516002909101805460609093015160ff166401000000000264ffffffffff1990931660e09290921c91909117919091179055610b8281610bbe565b6040518181527fdce08ffd49d3e7091684341cab08a4ffba42fdb443cb0deb2c92729772f4704b9060200160405180910390a195945050505050565b335f9081526003602052604090205460ff16610c0d5760405162461bcd60e51b815260206004820152600e60248201526d115e18d95c1d1a5bdb8818d85b1b60921b604482015260640161033f565b5f81815260046020908152604080832033845290915290205415610c645760405162461bcd60e51b815260206004820152600e60248201526d52657065617420636f6e6669726d60901b604482015260640161033f565b5f818152600960209081526040808320600201546005835281842054600690935292205460e09290921b916001335f9081526003602052604090205460ff918216911603610d2f576001600160e01b031983165f9081526008602052604090205460ff168210610d215760405162461bcd60e51b815260206004820152602260248201527f5369676e204e756d626572206f6620537570657241646d696e204c696d697465604482015261321760f11b606482015260840161033f565b610d2c826001611028565b91505b335f9081526003602052604090205460ff1660011901610dc1576001600160e01b031983165f9081526007602052604090205460ff168110610db35760405162461bcd60e51b815260206004820152601d60248201527f5369676e204e756d626572206f662041646d696e204c696d697465642e000000604482015260640161033f565b610dbe816001611028565b90505b6001600160e01b031983165f9081526007602052604090205460ff168110801590610e0757506001600160e01b031983165f9081526008602052604090205460ff168210155b15610e1557610e158461104d565b5f848152600460209081526040808320338452825280832060019055600390915290205460ff165f1901610e54575f8481526005602052604090208290555b335f9081526003602052604090205460ff1660011901610e7f575f8481526006602052604090208190555b60408051338152602081018690527f5cbe105e36805f7820e291f799d5794ff948af2a5f664e580382defb63390041910160405180910390a150505050565b6002545f9060041015610f0b5760405162461bcd60e51b8152602060048201526015602482015274139d5b58995c881bd98810591b5a5b88131a5b5a5d605a1b604482015260640161033f565b600280546001810182555f8290527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace0180546001600160a01b0319166001600160a01b0385161790555b6001600160a01b03929092165f908152600360205260409020805460ff191660ff9093169290921790915550600190565b6001545f9060031015610fdb5760405162461bcd60e51b815260206004820152601a60248201527f4e756d626572206f6620537570657241646d696e204c696d6974000000000000604482015260640161033f565b6001805480820182555f8290527fb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf60180546001600160a01b0319166001600160a01b038516179055610f55565b5f80611034838561149a565b90508381101561104657611046611654565b9392505050565b5f81815260096020526040902060020154640100000000900460ff16156110a75760405162461bcd60e51b815260206004820152600e60248201526d526570656174206578656375746560901b604482015260640161033f565b5f81815260096020526040808220805491516001600160a01b039092169183916110d691600190910190611668565b5f6040518083038185875af1925050503d805f8114611110576040519150601f19603f3d011682016040523d82523d5f602084013e611115565b606091505b50509050806111595760405162461bcd60e51b815260206004820152601060248201526f115e1958dd5d1a5bdb8819985a5b195960821b604482015260640161033f565b5f82815260096020908152604091829020600201805464ff00000000191664010000000017905590518381527fae30dc3f11bb6b178aafe5e7fc568fb6d87200068a944a8015c0db1b4533dbb8910160405180910390a15050565b80356001600160a01b03811681146104b8575f80fd5b5f80604083850312156111db575f80fd5b823591506111eb602084016111b4565b90509250929050565b5f60208284031215611204575f80fd5b5035919050565b5f6020828403121561121b575f80fd5b611046826111b4565b602080825282518282018190525f918401906040840190835b818110156112645783516001600160a01b031683526020938401939092019160010161123d565b509095945050505050565b602080825282516001600160a01b0316828201528281015160806040840152805160a084018190525f928190830160c086015e5f60c0828601015263ffffffff60e01b604086015116606085015260ff606086015116608085015260c0601f19601f8301168501019250505092915050565b634e487b7160e01b5f52604160045260245ffd5b5f82601f830112611304575f80fd5b813567ffffffffffffffff81111561131e5761131e6112e1565b604051601f8201601f19908116603f0116810167ffffffffffffffff8111828210171561134d5761134d6112e1565b604052818152838201602001851015611364575f80fd5b816020850160208301375f918101602001919091529392505050565b5f60208284031215611390575f80fd5b813567ffffffffffffffff8111156113a6575f80fd5b6113b2848285016112f5565b949350505050565b803560ff811681146104b8575f80fd5b5f805f606084860312156113dc575f80fd5b83356001600160e01b0319811681146113f3575f80fd5b9250611401602085016113ba565b915061140f604085016113ba565b90509250925092565b5f8060408385031215611429575f80fd5b611432836111b4565b9150602083013567ffffffffffffffff81111561144d575f80fd5b611459858286016112f5565b9150509250929050565b6020808252600990820152682337b93134b23232b760b91b604082015260600190565b634e487b7160e01b5f52601160045260245ffd5b80820180821115610a1a57610a1a611486565b634e487b7160e01b5f52603260045260245ffd5b81810381811115610a1a57610a1a611486565b634e487b7160e01b5f52603160045260245ffd5b600181811c908216806114fc57607f821691505b60208210810361151a57634e487b7160e01b5f52602260045260245ffd5b50919050565b60ff8181168382160190811115610a1a57610a1a611486565b634e487b7160e01b5f52602160045260245ffd5b601f82111561159457805f5260205f20601f840160051c810160208510156115725750805b601f840160051c820191505b81811015611591575f815560010161157e565b50505b505050565b815167ffffffffffffffff8111156115b3576115b36112e1565b6115c7816115c184546114e8565b8461154d565b6020601f8211600181146115f9575f83156115e25750848201515b5f19600385901b1c1916600184901b178455611591565b5f84815260208120601f198516915b828110156116285787850151825560209485019460019092019101611608565b508482101561164557868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b634e487b7160e01b5f52600160045260245ffd5b5f808354611675816114e8565b60018216801561168c57600181146116a1576116ce565b60ff19831686528115158202860193506116ce565b865f5260205f205f5b838110156116c6578154888201526001909101906020016116aa565b505081860193505b50919594505050505056fea264697066735822122064caee971a699212627906215ff608bbaa51716551807c321a80b4b23e0aeb3164736f6c634300081a0033";

    private static String librariesLinkedBinary;

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

    public static RemoteCall<MultiSignWallet> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MultiSignWallet.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    public static RemoteCall<MultiSignWallet> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MultiSignWallet.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<MultiSignWallet> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MultiSignWallet.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<MultiSignWallet> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MultiSignWallet.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
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
