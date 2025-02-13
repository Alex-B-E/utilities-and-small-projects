# SFTP Client

This project is an SFTP client that allows you to upload and download files using the SFTP protocol.

## Project Structure

```plaintext
sftp-client/
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── sftpclient/
│       │           └── SftpClient.java
│       └── resources/
│           └── config.properties
├── target/
├── sftp-client.sh
├── sftp-client.bat
├── pom.xml
└── README.md
```

## Navigation

Navigate to the project's root directory:

```bash
cd path/to/sftpClient
```

## Compilation and Packaging

Compile and package the project:

```bash
mvn clean package
```

## Execution

### Usage Example for Linux/Mac

#### Upload files:

```bash
./sftp-client.sh upload [host] [user] [password] [remoteDir] [localFilePath]
```

#### Download files:

```bash
./sftp-client.sh download [host] [user] [password] [remoteDir] [downloadPath]
```

### Usage Example for Windows

#### Upload files:

```cmd
sftp-client.bat upload [host] [user] [password] [remoteDir] [localFilePath]
```

#### Download files:

```cmd
sftp-client.bat download [host] [user] [password] [remoteDir] [downloadPath]
```

## Execution Examples

### Example for Uploading Files on Linux/Mac

```bash
./sftp-client.sh upload sftp.example.com myuser mypassword /remote/dir /path/to/local/file
```

### Example for Downloading Files on Linux/Mac

```bash
./sftp-client.sh download sftp.example.com myuser mypassword /remote/dir /path/to/download/file
```

### Example for Uploading Files on Windows

```cmd
sftp-client.bat upload sftp.example.com myuser mypassword /remote/dir \path\to\local\file
```

### Example for Downloading Files on Windows

```cmd
sftp-client.bat download sftp.example.com myuser mypassword /remote/dir \path\to\download\file
```

## Default Configuration

If some parameters are not provided, default values from the `config.properties` file located in `src/main/resources/config.properties` will be used.