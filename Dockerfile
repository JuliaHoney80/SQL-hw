FROM ubuntu:latest
LABEL authors="medov"

ENTRYPOINT ["top", "-b"]