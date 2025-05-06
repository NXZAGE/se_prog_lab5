package itmo.nxzage.client;

import itmo.nxzage.client.parsing.ErrorResponse;
import itmo.nxzage.client.parsing.Parser;
import itmo.nxzage.client.parsing.Response;
import itmo.nxzage.client.parsing.SuccessfulResponse;
import itmo.nxzage.common.commands.Command;
import itmo.nxzage.common.commands.CommandType;
import itmo.nxzage.common.commands.client.ExecuteScript;

public final class Client {
    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        while(true) {
            Response response = parser.getCommand();
            if (response.successful()) {
                System.out.println("Successful! Command: "+ response.getClass());
                SuccessfulResponse suc = (SuccessfulResponse) response;
                Command cmd = suc.getCommand();
                System.out.println(cmd.getClass());
                if (cmd.getType() == CommandType.EXIT) {
                    break;
                }
                if (cmd.getType() == CommandType.EXECUTE_SCRIPT) {
                    parser.switchToNewSource(((ExecuteScript) cmd).getFileName());
                }
            } else {
                ErrorResponse err = (ErrorResponse) response;
                System.out.println(err.getDescription());
            }
        }
    }
}
