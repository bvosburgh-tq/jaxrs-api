#!/bin/bash

# The Maven build generates a PDF with half-parsed Asciidoctor 'latexmath' macros.
# This is because Asciidoctor uses Asciidoctor PDF to generate PDFs, and
# Asciidoctor PDF does not provide in-built support for STEM/LaTEX math macros.
# See https://asciidoctor.org/docs/asciidoctor-pdf/#stem-support.
# Asciidoctor Mathematical is an extension for Asciidoctor that will handle
# the 'latexmath' macros; but Asciidoctor Mathematical is a Ruby gem that
# uses native extensions, and those extensions can be built on only Linux or MacOS.
# Also, those extensions can be called from only "native" Ruby, not JRuby;
# and, unfortunately, Maven uses JRuby. As a result, the PDF version of
# the spec must be generated manually.
# 
# Here are some [untested] steps to configure Asciidoctor PDF etc.
# All steps should be verified....
# Platform: Ubuntu 20.04
#
# - Check out release commit
#   git checkout tags/3.0.0 -b bjv/3.0.0
# - Install Ruby and Asciidoctor PDF and pre-requisites
#   sudo apt install ruby
#   sudo gem install rghost
#   sudo gem install text-hyphen
#   sudo gem install asciidoctor-pdf
#   sudo apt-get -qq -y install bison flex libffi-dev libxml2-dev libgdk-pixbuf2.0-dev libcairo2-dev libpango1.0-dev fonts-lyx cmake
#   sudo apt install ruby-dev
#   sudo gem install asciidoctor-mathematical
#   sudo gem install coderay
# 
# Source changes:
# - Mathematical does not seem to handle the LaTEX \mbox command;
#   so remove those from the source file.
# - Without the \mbox commands:
#   - Text spacing must be handled explicitly (e.g. ' ' -> '\; ')
#   - Variables nested within the text must not be delimited (e.g. '$A$' -> 'A')
#   - Hyphens must be coded explicitly (e.g. '-' -> '{\textrm{-}}')
#   - Carets must be coded explicitly (e.g. '^' -> '\hat{}')
# - Nested ordered lists are not handled correctly; so their enumerations
#   must be stated explicitly (e.g. '[loweralpha]', '[lowerroman]').
# - Variables nested within \mbox text

# Asciidoctor PDF command to generate reasonable PDF:

asciidoctor-pdf \
  -r asciidoctor-mathematical \
  -a mathematical-format=svg \
  -a imagesdir=images \
  -a pdf-stylesdir=../../theme \
  -a pdf-style=jakartaee \
  -a revnumber=3.0.0 \
  -a revdate="September 23, 2020" \
  -a doctype=book \
  -a status="" \
  -a data-uri \
  -a icons=font \
  -a pagenums=true \
  -a toc \
  -a sectanchors \
  -a idprefix \
  -a idseparator=- \
  -a docinfo1=true \
  -a embedAssets=true \
  spec.adoc
