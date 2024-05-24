SERVICES = api-gateway auth-service config-server course-service eureka-server mail-service user-service

.PHONY: all $(SERVICES)
all: $(SERVICES)

$(SERVICES):
	@echo "Building $@..."
	@$(MAKE) -C $@

.PHONY: clean

clean:
	@for service in $(SERVICES); do \
		echo "Cleaning $$service..."; \
		$(MAKE) -C $$service clean; \
	done
