package main;

import java.io.*;
import java.net.*;

public class DownloadAttachments {

    public static void main(String[] args) {

        String urlString = "https://www.google.com/"; // replace this line with PNC jira URL
        String downloadFolderPath = "C:\\Project\\"; // replace with your desired download folder path

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("href=\"") || line.contains("attachment") || line.endsWith(".pdf") || line.endsWith(".jpg") || line.endsWith(".doc") || line.endsWith(".docx") || line.endsWith(".png")) {
                    String attachmentUrl = line.substring(line.indexOf("href=\"") + 6, line.indexOf("\"", line.indexOf("href=\"") + 6));
                    downloadFile(attachmentUrl, downloadFolderPath);
                }
            }

            reader.close();
            inputStream.close();

            System.out.println("All attachments downloaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void downloadFile(String fileUrl, String downloadFolderPath) throws IOException {

        URL url = new URL(fileUrl);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        String fileName = "attachments.doc";
        File file = new File(downloadFolderPath + fileName);
        OutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[4096];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();

        System.out.println("Attachment downloaded: " + fileName);

    }

}
