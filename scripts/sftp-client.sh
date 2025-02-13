#!/bin/bash

# Path to the JAR file
JAR_FILE="target/sftp-client-1.0-SNAPSHOT-shaded.jar"

# Function to display usage
usage() {
  echo "Usage: $0 [upload|download] [host] [user] [password] [remoteDir] [localFilePath|downloadPath]"
  echo "If any parameter is not provided, the default from src/main/resources/config.properties will be used."
}

# Check if the correct number of arguments is provided
if [ "$#" -lt 1 ]; then
  usage
  exit 1
fi

# Run the operation
java -jar $JAR_FILE "$@"