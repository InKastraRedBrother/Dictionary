package Dictionary;

/**
 * Point of entry
 */
class Main {

    public static void main(String[] args) {
        CommunicationWithConsole communicationWithConsole = new CommunicationWithConsole();
        IOStream ioStream = new IOStream();
        Start start = new Start(communicationWithConsole, ioStream);
        start.runApp();

    }
}




