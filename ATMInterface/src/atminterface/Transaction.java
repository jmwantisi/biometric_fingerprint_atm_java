/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atminterface;

/**
 *
 * @author jmwantisi
 */
public class Transaction {

    private static int customerAccountNumber;
    private static int customerATMCardNumber;
    private static int withdrawalAmount;
    private static double accountBalance;
    private static double amountTransfered;

    public Transaction() {
    }

    public void sendCashWithdrawalRequestToServer(int account, int cardNo, int amount) {

    }

    public Integer recieveCashWithdrawalResponseFromServer() {

        return withdrawalAmount;
    }

    public void sendBalanceRequestToServer(int account, int cardNo) {

    }

    public double recieveBalanceResponseFromServer() {

        return accountBalance;
    }

    public void sendFundTransferRequestToServer(int account, int cardNo, double amount) {

    }

    public double recieveFundTransferResponseFromServer() {

        return amountTransfered;
    }

    public void sendBillPaymentRequestToServer(int account, int cardNo, double amount) {

    }

    public double recieveBillPaymentResponseFromServer() {

        return amountTransfered;
    }

}
