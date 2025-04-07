package itmo.nxzage.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public final class DumpManager {
    public enum Status {
        SUCCESS(false), ACCESS_DENIED(true), WRONG_NAME(
                true), UNRECOGNIZED_ERROR(true); // может быть ошибка с двойным подключением FileReader/writer

        private Boolean error;

        private Status(Boolean error) {
            this.error = error;
        }

        public Boolean isSuccess() {
            return this.error == false;
        }

        public Boolean isError() {
            return this.error;
        }
    }

    public class Response implements ActionResponse<Status> {
        private Status status;
        private String message;

        public Response(Status status, String message) {
            this.status = status;
            this.message = message;
        }

        public Status status() {
            return this.status;
        }

        public String message() {
            return this.message;
        }

        public Boolean successful() {
            return this.status.isSuccess();
        }

        public Boolean hasError() {
            return this.status.isError();
        }

        public String errorMessage() {
            if (this.hasError() == false) {
                throw new IllegalAccessError(
                        "Non-error responses don't contain errorMessage!"
                );
            }

            return this.message();
        }
    }

    public final class WritingResponse extends Response {
        private static final String SUCCESS_MESSAGE_PATTERN =
                "Successfully written!\nFile: %s\nStrings written: %d";
        private String destinationPath;
        private Integer stringsCount;

        public WritingResponse(String destinationPath, Integer stringsCount) {
            super(Status.SUCCESS, String.format(SUCCESS_MESSAGE_PATTERN,
                    destinationPath, stringsCount));
            this.destinationPath = destinationPath;
            this.stringsCount = stringsCount;
        }

        public WritingResponse(Status error, String errorMessage) {
            super(error, errorMessage);

            if (error.isSuccess()) {
                throw new IllegalArgumentException(
                        "Error-response can\'t be initialized with successful status");
            }
        }

        public String getDestinationPath() {
            if (this.hasError()) {
                throw new IllegalAccessError(
                        "Error response doesn\'t contain DestinationPath"); // bad exception
            }

            return this.destinationPath;
        }

        public Integer getStringsCount() {
            if (this.hasError()) {
                throw new IllegalAccessError(
                        "Error response doesn\'t contain stringsCount"); // bad exception
            }

            return this.stringsCount;
        }
    }

    public final class ReadingResponse extends Response {
        private static final String SUCCESS_MESSAGE_PATTERN =
                "Successfully read!";
        private String data;

        public ReadingResponse(String data) {
            super(Status.SUCCESS, String.format(SUCCESS_MESSAGE_PATTERN));
            this.data = data;
        }

        public ReadingResponse(Status error, String errorMessage) {
            super(error, errorMessage);
            if (error.isSuccess()) {
                throw new IllegalArgumentException(
                        "Error-response can\'t be initialized with successful status");
            }
        }

        public String getData() {
            if (this.hasError()) {
                throw new IllegalAccessError(
                        "Error-respons doesn\'t contain read data");
            }

            return this.data;
        }
    }

    private File file;

    public DumpManager(String filePath) {
        this.file = new File(filePath);
    }

    public String getPath() {
        return this.file.getAbsolutePath();
    }

    public WritingResponse write(String data) {
        try (FileWriter writer = new FileWriter(this.file)) {
            writer.write(data);
            return new WritingResponse("Path", 10); // no strings count
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
            return new WritingResponse(Status.UNRECOGNIZED_ERROR,
                    exc.getMessage());
            // сделать распознавание ошибок
        }
    }

    public ReadingResponse read() {
        String result = new String();
        try (InputStreamReader reader =
                new InputStreamReader(new FileInputStream(this.file))) {
            char[] buf = new char[256];
            int count = 0;
            final int OFFSET = 0;
            while ((count = reader.read(buf)) != -1) {
                result += new String(buf, OFFSET, count);
            }
            return new ReadingResponse(result);
        } catch (IOException exc) {
            return new ReadingResponse(Status.UNRECOGNIZED_ERROR,
                    exc.getMessage());
            // сделать распознавание ошибок
        }
    }
}
