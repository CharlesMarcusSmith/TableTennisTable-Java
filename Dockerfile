FROM gradle:7.5.1
# Base build we are working from

WORKDIR /app
# good to have as it points to the directory for the app
COPY . .
# copies files into the image
# copy everything in the listed directory into the image

RUN ./gradlew build

CMD ./gradlew run
# run above command in cmd




