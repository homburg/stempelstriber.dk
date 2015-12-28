.PHONY: run

.SUFFIXES:

run:
	GITKIT_CONFIG_JSON=$(PWD)/gitkit-server-conf.json PLAY_SECRET="test-secret" IMAGE_PROXY_HOST="http://image.stempelstriber.dk/" sbt run
