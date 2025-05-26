# ===============================
# ===== Makefile parameters =====
# ===============================

JAVAC ?= javac
JAR ?= jar
OUTPUT ?= interpreter.jar

MAIN ?= Main
SRCDIR ?= src
CLASSDIR ?= classes

# ==============================
# ===== Makefile internals =====
# ==============================

SOURCES := $(shell find $(SRCDIR) -name "*.java")

.PHONY: build
build: | $(CLASSDIR)
	$(JAVAC) -sourcepath $(SOURCES) -d $(CLASSDIR) $(SOURCES)

$(CLASSDIR):
	@mkdir -p "$@"

.PHONY: package
package: build
	@$(JAR) -cfm $(OUTPUT) $(SRCDIR)/Manifest.txt -C $(CLASSDIR) .

.PHONY: clean
clean:
	rm -rf $(CLASSDIR)
	rm -f $(OUTPUT)