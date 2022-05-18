package Dictionary;

/**
 * Point of entry
 */
class Main {

    public static void main(String[] args) {
        CommunicationWithConsole communicationWithConsole = new CommunicationWithConsole();
        Start start = new Start(communicationWithConsole);
        start.runApp();

    }
}




