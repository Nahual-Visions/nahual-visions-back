FROM ubuntu:latest
LABEL authors="Peculiar"

ENTRYPOINT ["top", "-b"]