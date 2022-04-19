start-up: register-schemas-with-delay build-project
	@echo -e "\nproject started succesfully."

run-services:
	@echo -e "\n${@}" && docker-compose up -d

build-project: run-services
	@echo -e "\n${@}" && gradle -q clean build

register-schemas-with-delay: run-services
	@echo -e "\n${@}" && sleep 10 && gradle -q registerSchemasTask

register-schemas:
	gradle registerSchemasTask

kill-services: ## Kill and remove service's containers
	@docker-compose kill && docker-compose rm -f
