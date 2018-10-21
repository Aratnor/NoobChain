package blockChain;

import transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;

public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); // //our data will be a simple message.
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;

    public Block(String previusHash) {
        this.previousHash = previusHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot);
        return calculatedHash;
    }
    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty);
        while(!hash.substring(0,difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block mined!! :" +hash);
    }

    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unlees block is genesis then ignore.
        if(transaction == null) return false;
        if(previousHash != "0") {
            if(transaction.processTransaction() != true) {
                System.out.println(transaction.processTransaction() != true);
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
