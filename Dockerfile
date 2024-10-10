FROM gradle:7.5.1 as base
# Base build we are working from - label of base used in other from's later

WORKDIR /app
# good to have as it points to the directory for the app
COPY . .
# copies files into the image
# copy everything in the listed directory into the image

RUN ./gradlew build
# RUN will execute before ENTRYPOINT no matter what order they are written

FROM base AS prod
ENTRYPOINT ./gradlew run --console=plain
# run above command in cmd
# use: docker build --target prod --tag table-tennis-table:production .
# run with: docker run -it table-tennis-table:production
# table-tennis-table:production is the tag we gave it
# ^ the :name is looked for by docker which tag to use
# imagename = table-tennis-table:production
# the tag is part of the image name

FROM base AS test
ENTRYPOINT ./gradlew test --console=plain
# run above command in cmd
# docker build --target test --tag table-tennis-table:testing .
# run with: docker run -it table-tennis-table:testing



