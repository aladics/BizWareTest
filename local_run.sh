set -a
source .env
env ./gradlew build
env docker compose up -d --build --force-recreate