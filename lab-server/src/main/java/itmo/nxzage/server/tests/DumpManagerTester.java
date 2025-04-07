package itmo.nxzage.server.tests;

import itmo.nxzage.server.DumpManager;
import itmo.nxzage.server.Server;

public class DumpManagerTester {
    public enum CaseType {
        READ,
        WRITE
    }

    public class Case {
        protected CaseType type;
        protected String filePath;

        Case(CaseType type, String filePath) {
            this.type = type;
            this.filePath = filePath;
        }
    }

    public final class ReadCase extends Case {
        private String expectedData;

        ReadCase (String filePath, String expectedData) {
            super(CaseType.READ, filePath);
            this.expectedData = expectedData;
        }
    }

    public static void tryRead(DumpManager dumper) {
        final String messagePatternHeader =
                "---DUMPER TRIES READ TEST CASE----------------------------\n"
                        + "---FilePath: \"%s\"\n" + "---DumperConnected: %s\n"
                        + "---Status: %s\n";
        final String messagePatternFooter =
                "----------------------------------------------------------\n";
        final String successMessagePattern =
                messagePatternHeader + "---Data: ==next string==>\n%s\n";
        final String errorMessagePattern = messagePatternHeader
                + "---Error Message: [%s]\n" + messagePatternFooter;

        String filePath = dumper.getPath();
        String connected = (filePath != null) ? "true" : "false";
        DumpManager.ReadingResponse response = dumper.read();
        String status = response.hasError() ? "ERROR" : "SUCCESS";
        if (response.hasError()) {
            String message = String.format(errorMessagePattern, filePath,
                    connected, status, response.errorMessage());

            System.out.println(message);
            return;
        }

        if (response.successful()) {
            String message = String.format(successMessagePattern, filePath,
                    connected, status, response.getData());

            System.out.println(message);
            return;
        }   
    }

    public static void tryWrite(DumpManager dumper, String data) {
        final String messagePatternHeader =
                "---DUMPER TRIES WRITE TEST CASE----------------------------\n"
                        + "---FilePath: \"%s\"\n" + "---DumperConnected: %s\n"
                        + "---Status: %s\n";
        final String messagePatternFooter =
                "----------------------------------------------------------\n";
        final String successMessagePattern =
                messagePatternHeader + "---Data: written\n";
        final String errorMessagePattern = messagePatternHeader
                + "---Error Message: [%s]\n" + messagePatternFooter;

        String filePath = dumper.getPath();
        String connected = (filePath != null) ? "true" : "false";
        DumpManager.WritingResponse response = dumper.write(data);
        String status = response.hasError() ? "ERROR" : "SUCCESS";
        if (response.hasError()) {
            String message = String.format(errorMessagePattern, filePath,
                    connected, status, response.errorMessage());

            System.out.println(message);
            return;
        }

        if (response.successful()) {
            String message = String.format(successMessagePattern, filePath,
                    connected, status);

            System.out.println(message);
            return;
        }   
    }
}
