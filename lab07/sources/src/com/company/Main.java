package com.company;

import com.company.dao.*;
import com.company.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final PaymentDao paymentDao = new PaymentDao();
    private static final AccruedReceivableDao accruedReceivableDao = new AccruedReceivableDao();
    private static final PriceListDao priceListDao = new PriceListDao();
    private static final InstallationDao installationDao = new InstallationDao();
    private static final ClientDao clientDao = new ClientDao();
    private static LocalDate currentDate = LocalDate.now();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        List<Installation> list = installationDao.getAll();

        for ( int i=0; i< list.size(); i ++){
            list.get(i).getClient().setClientNumber(i);
            installationDao.update(list.get(i));
        }
        setUpTimeThread().start();

        do {
            showMainMenu();
            choice = scanner.nextInt();
            mainMenuSwitch(choice, scanner);
        } while (choice != 0);

    }

    private static void showMainMenu() {
        System.out.println("Choose table which you want to manage:");
        System.out.println("0. Exit");
        System.out.println("1. client");
        System.out.println("2. installation");
        System.out.println("3. price_list");
        System.out.println("4. accrued_receivable");
        System.out.println("5. payment");
    }

    private static void mainMenuSwitch(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                clientTableManagement(scanner);
                break;
            case 2:
                installationTableManagement(scanner);
                break;
            case 3:
                priceListTableManagement(scanner);
                break;
            case 4:
                accruedReceivableTableManagement(scanner);
                break;
            case 5:
                paymentTableManagement(scanner);
                break;
            case 0:
                System.exit(0);
            default:
                System.out.println("Entered value isn't correct");
                break;
        }
    }

    private static void paymentTableManagement(Scanner scanner) {
        long id;
        LocalDate paymentDate;
        float paymentAmount;
        Installation installation;
        int action;
        do {
            System.out.println("Management of payment table:");
            System.out.println("0. Return");
            System.out.println("1. Add new payment");
            System.out.println("2. Update payment");
            System.out.println("3. Display all payments");
            action = scanner.nextInt();

            switch (action) {
                case 1:
                    System.out.println("Enter id of a new payment:");
                    id = scanner.nextInt();
                    System.out.println("Enter payment date of a new payment \"RRRR--MM-DD\":");
                    paymentDate = LocalDate.parse(scanner.next());
                    System.out.println("Enter payment amount of a new payment:");
                    paymentAmount = scanner.nextFloat();
                    System.out.println("Enter installation's id of a new payment:");
                    installation = paymentDao.getInstallationDao().get(scanner.nextInt());
                    paymentDao.save(new Payment(id, paymentDate, paymentAmount, installation));
                    System.out.println("Payment was added successfully!");
                    break;
                case 2:
                    System.out.println("Enter id of payment:");
                    id = scanner.nextInt();
                    System.out.println("Enter payment date \"RRRR-MM-DD\":");
                    String dateInput = scanner.next();
                    paymentDate = LocalDate.parse(dateInput);
                    System.out.println("Enter payment amount:");
                    paymentAmount = scanner.nextFloat();
                    System.out.println("Enter installation's id of a payment:");
                    installation = paymentDao.getInstallationDao().get(scanner.nextInt());
                    paymentDao.update(new Payment(id, paymentDate, paymentAmount, installation));
                    System.out.println("Payment was updated successfully!");
                    break;
                case 3:
                    System.out.println("id\t|  " + "payment_date\t" + "|\tpayment_amount\t|" + "installation_id|");
                    paymentDao.getAll().forEach(payment -> System.out.println(payment.getId() + "\t|\t" +
                            payment.getPaymentDate() + "\t|\t\t" + payment.getPaymentAmount() +
                            "\t\t|\t\t" + payment.getInstallation().getId() + "\t\t|"));
                    break;
                case 0:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Entered value isn't correct");
                    break;
            }
        } while (action != 0);
    }

    private static void accruedReceivableTableManagement(Scanner scanner) {
        int action;
        LocalDate deadline;
        float amountToPay;
        Installation installation;
        do {
            System.out.println("Management of accrued_receivable table:");
            System.out.println("0. Return");
            System.out.println("1. Add new accrued receivable");
            System.out.println("2. Display all accrued receivables");

            action = scanner.nextInt();

            switch (action) {
                case 1:
                    System.out.println("Enter new accrued receivable deadline \"RRRR-MM-DD\":");
                    String dateInput = scanner.next();
                    deadline = LocalDate.parse(dateInput);
                    System.out.println("Enter amount to pay:");
                    amountToPay = scanner.nextFloat();
                    System.out.println("Enter installation's id:");
                    installation = installationDao.get(scanner.nextInt());
                    accruedReceivableDao.save(new AccruedReceivable(deadline, amountToPay, installation));
                    System.out.println("New accrued receivable was added successfully!");

                    break;
                case 2:
                    System.out.println("id\t|  " + "payment_deadline\t" + "|\tamount_to_pay\t|" + "installation_id|");
                    accruedReceivableDao.getAll().forEach(ar -> System.out.println(ar.getId() + "\t|\t" +
                            ar.getPaymentDeadline().toString() + "\t\t|\t\t" + ar.getAmountToPay() +
                            "\t\t|\t\t" + ar.getInstallation().getId() + "\t\t|"));
                    break;
                case 0:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Entered value isn't correct");
                    break;
            }
        } while (action != 0);
    }

    private static void priceListTableManagement(Scanner scanner) {

        long id;
        String serviceType;
        float price;
        int action;
        do {
            System.out.println("Management of price_list table:");
            System.out.println("0. Return");
            System.out.println("1. Add new price_list record");
            System.out.println("2. Update price_list record");
            System.out.println("3. Delete price_list record");
            action = scanner.nextInt();

            switch (action) {
                case 1:
                    System.out.println("Enter service type of a new price_list record:");
                    serviceType = scanner.next();
                    System.out.println("Enter price of a new price_list record:");
                    price = scanner.nextFloat();
                    priceListDao.save(new PriceList(serviceType, price));
                    System.out.println("Price_list was added successfully!");
                    break;
                case 2:
                    System.out.println("Enter id of price_list record:");
                    id = scanner.nextInt();
                    System.out.println("Enter service type of price_list record:");
                    serviceType = scanner.next();
                    System.out.println("Enter price of price_list record:");
                    price = scanner.nextFloat();
                    priceListDao.update(new PriceList(id, serviceType, price));
                    System.out.println("Price_list was updated successfully!");
                    break;
                case 3:
                    System.out.println("Enter price_list id:");
                    id = scanner.nextInt();
                    priceListDao.delete(new PriceList(id));
                    System.out.println("Price_list was deleted successfully!");
                    break;
                case 0:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Entered value isn't correct");
                    break;
            }
        } while (action != 0);
    }

    private static void installationTableManagement(Scanner scanner) {
        int action;
        long id;
        String routerNumber;
        String serviceType;
        String city;
        String address;
        String postcode;
        Client client;
        PriceList priceList;
        do {

            System.out.println("Management of installation table:");
            System.out.println("0. Return");
            System.out.println("1. Add new installation");
            System.out.println("2. Update installation");
            System.out.println("3. Delete installation");
            action = scanner.nextInt();

            switch (action) {
                case 1:
                    System.out.println("Enter router number:");
                    routerNumber = scanner.next();
                    System.out.println("Enter city:");
                    city = scanner.next();
                    System.out.println("Enter address:");
                    address = scanner.next();
                    System.out.println("Enter postcode:");
                    postcode = scanner.next();
                    System.out.println("Enter client's id:");
                    client = installationDao.getClientDao().get(scanner.nextInt());
                    System.out.println("Enter price list id:");
                    priceList = installationDao.getPriceListDao().get(scanner.nextInt());
                    serviceType = priceList.getServiceType();
                    installationDao.save(new Installation(routerNumber, serviceType, city, address,
                            postcode, client, priceList));
                    System.out.println("Installation was added successfully!");
                    break;
                case 2:
                    System.out.println("Enter installation's id:");
                    id = scanner.nextInt();
                    System.out.println("Enter router number:");
                    routerNumber = scanner.next();
                    System.out.println("Enter service type:");
                    serviceType = scanner.next();
                    System.out.println("Enter city:");
                    city = scanner.next();
                    System.out.println("Enter address:");
                    address = scanner.next();
                    System.out.println("Enter postcode:");
                    postcode = scanner.next();
                    System.out.println("Enter client's id:");
                    client = installationDao.getClientDao().get(scanner.nextInt());
                    System.out.println("Enter price list id:");
                    priceList = installationDao.getPriceListDao().get(scanner.nextInt());
                    installationDao.update(new Installation(id, routerNumber, serviceType, city, address,
                            postcode, client, priceList));
                    System.out.println("Installation was updated successfully!");
                    break;
                case 3:
                    System.out.println("Enter installation's id:");
                    id = scanner.nextInt();
                    installationDao.delete(new Installation(id));
                    System.out.println("Client was deleted successfully!");
                    break;
                case 0:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Entered value isn't correct");
                    break;
            }
        } while (action != 0);
    }

    private static void clientTableManagement(Scanner scanner) {

        int action;
        String clientName;
        String clientLastName;
        long clientId;
        do {
            System.out.println("Management of client table:");
            System.out.println("0. Return");
            System.out.println("1. Add new client");
            System.out.println("2. Update client");
            System.out.println("3. Delete client");
            action = scanner.nextInt();

            switch (action) {
                case 1:
                    System.out.println("Enter name of a new client:");
                    clientName = scanner.next();
                    System.out.println("Enter last name of a new client:");
                    clientLastName = scanner.next();
                    clientDao.save(new Client(clientName, clientLastName));
                    System.out.println("Client was added successfully!");
                    break;
                case 2:
                    System.out.println("Enter new client's id:");
                    clientId = scanner.nextInt();
                    System.out.println("Enter new client's name:");
                    clientName = scanner.next();
                    System.out.println("Enter new client's last name:");
                    clientLastName = scanner.next();
                    clientDao.update(new Client(clientId, clientName, clientLastName));
                    System.out.println("Client was updated successfully!");
                    break;
                case 3:
                    System.out.println("Enter client's id:");
                    clientId = scanner.nextInt();
                    clientDao.delete(new Client(clientId));
                    System.out.println("Client was deleted successfully!");
                    break;
                case 0:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Entered value isn't correct");
                    break;
            }
        } while (action != 0);
    }

    private static Thread setUpTimeThread() {

        return new Thread(() -> {
            while (true) {

                List<AccruedReceivable> accruedReceivables = accruedReceivableDao.getAll();
                List<Payment> payments = paymentDao.getAll();

                for (AccruedReceivable currentAR : accruedReceivables) {

                    long paymentSum = 0;
                    long receivablesSum = 0;

                    if (currentAR.getPaymentDeadline().equals(currentDate)) {

                        long currentInstallationId = currentAR.getInstallation().getId();
                        Installation currInstallation = installationDao.get(currentInstallationId);
                        Client currentClient = currInstallation.getClient();

                        for (Payment payment : payments) {
                            if (payment.getInstallation().getId() == currentInstallationId) {
                                paymentSum += payment.getPaymentAmount();
                            }
                        }

                        for (AccruedReceivable aR : accruedReceivables) {
                            if (currentInstallationId == aR.getInstallation().getId()) {
                                receivablesSum += aR.getAmountToPay();
                            }
                        }

                        if (receivablesSum > paymentSum) {
                            try {
                                FileWriter fileWriter = new FileWriter("logs.txt", true);
                                BufferedWriter logs = new BufferedWriter(fileWriter);
                                logs.write("Installation id: " + currentAR.getInstallation().getId() + " Client: " +
                                        " " + currentClient.getFirstName() + " " + currentClient.getLastName() + " " +
                                        "owes " + " " + (receivablesSum - paymentSum) + "PLN - " +
                                        currentDate.toString() + "\n");
                                logs.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.err.println("Error while writing to file: ");
                            }
                        }

                        AccruedReceivable newAccruedReceivable = new AccruedReceivable(
                                currentDate.plusMonths(1),
                                currInstallation.getPriceList().getPrice(),
                                currentAR.getInstallation());
                        accruedReceivableDao.save(newAccruedReceivable);
                    }
                }

                currentDate = currentDate.plusDays(1);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
