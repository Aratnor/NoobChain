package blockChain;

import com.google.gson.GsonBuilder;
import transaction.Transaction;
import wallet.Wallet;

import java.security.Security;
import java.util.ArrayList;

public class NoobChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static Wallet walletA;
    public static Wallet walletB;

    public static void main(String[] args) {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();

        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
        //Create test transaction from WalletA to WalletB
        Transaction transaction = new Transaction(walletA.publicKey,walletB.publicKey,5,null);
        transaction.generateSignature(walletA.privateKey);

        System.out.println("Is signature verified");
        System.out.println(transaction.verifiySignature());
    }
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        for(int i = 1;i<blockchain.size();i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current hash is not equal");
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes is not equal");
                return false;
            }
        }
        return true;
    }


}
