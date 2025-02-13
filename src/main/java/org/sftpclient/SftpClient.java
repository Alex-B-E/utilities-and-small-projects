package org.sftpclient;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * SftpClient is a Java application that demonstrates how to upload and download files
 * using the SFTP protocol. It utilizes the JSch library for SFTP operations.
 */
public class SftpClient {
    private static final Logger logger = LoggerFactory.getLogger(SftpClient.class);

    /**
     * Main method to run the SftpClient application.
     *
     * @param args Command line arguments. Expected arguments:
     *             args[0] - operation ("upload" or "download")
     *             args[1] - (optional) host
     *             args[2] - (optional) user
     *             args[3] - (optional) password
     *             args[4] - (optional) remoteDir
     *             args[5] - (optional) localFilePath
     *             args[6] - (optional) downloadPath
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            logger.error("Por favor, proporciona la operación (upload/download) como argumento.");
            System.exit(1);
        }

        String operation = args[0];

        Properties config = loadConfig("src/main/resources/config.properties");

        // Overwrite properties if provided as arguments
        if (args.length > 1 && !args[1].isEmpty()) config.setProperty("host", args[1]);
        if (args.length > 2 && !args[2].isEmpty()) config.setProperty("user", args[2]);
        if (args.length > 3 && !args[3].isEmpty()) config.setProperty("password", args[3]);
        if (args.length > 4 && !args[4].isEmpty()) config.setProperty("remoteDir", args[4]);
        if (args.length > 5 && !args[5].isEmpty()) config.setProperty("localFilePath", args[5]);
        if (args.length > 6 && !args[6].isEmpty()) config.setProperty("downloadPath", args[6]);

        // Read configuration properties
        String host = config.getProperty("host");
        String user = config.getProperty("user");
        String password = config.getProperty("password");
        String remoteDir = config.getProperty("remoteDir");
        String localFilePath = config.getProperty("localFilePath") + "/file.txt";
        String remoteFilePath = remoteDir + "/file.txt";
        String downloadPath = config.getProperty("downloadPath") + "/file.txt";

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // Establish SSH session
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            logger.info("Conexión SSH establecida con {}", host);

            // Open SFTP channel
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            logger.info("Canal SFTP abierto");

            if ("upload".equalsIgnoreCase(operation)) {
                // Upload a file to the remote directory
                try (InputStream inputStream = Files.newInputStream(Paths.get(localFilePath))) {
                    channelSftp.put(inputStream, remoteFilePath);
                    logger.info("Fichero subido a {}", remoteFilePath);
                }
            } else if ("download".equalsIgnoreCase(operation)) {
                // Download the file from the remote directory
                try (OutputStream outputStream = Files.newOutputStream(Paths.get(downloadPath))) {
                    channelSftp.get(remoteFilePath, outputStream);
                    logger.info("Fichero descargado a {}", downloadPath);
                }
            } else {
                logger.error("Operación no reconocida: {}. Usa 'upload' o 'download'.", operation);
                System.exit(1);
            }
        } catch (Exception e) {
            logger.error("Error durante la operación SFTP", e);
        } finally {
            // Close the SFTP channel and SSH session
            if (channelSftp != null) {
                channelSftp.disconnect();
                logger.info("Canal SFTP cerrado");
            }
            if (session != null) {
                session.disconnect();
                logger.info("Sesión SSH cerrada");
            }
        }
    }

    /**
     * Loads the configuration properties from the specified file.
     *
     * @param filePath The path to the configuration file.
     * @return A Properties object containing the configuration properties.
     */
    private static Properties loadConfig(String filePath) {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("No se pudo cargar el archivo de configuración", e);
        }
        return properties;
    }
}