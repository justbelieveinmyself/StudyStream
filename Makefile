SERVICES = api-gateway auth-service config-server course-service eureka-server mail-service user-service

.PHONY: all $(SERVICES) clean-all docker-push-all
all: $(SERVICES)

$(SERVICES):
	@echo "Building $@..."
	@$(MAKE) -C $@ all

clean-all:
	@for service in $(SERVICES); do \
		echo "Cleaning $$service..."; \
		$(MAKE) -C $$service clean; \
	done

docker-push-all:
	@for service in $(SERVICES); do \
		echo "Pushing Docker image for $$service..."; \
		$(MAKE) -C $$service docker-push; \
	done

.PHONY: clean-all docker-push-all
