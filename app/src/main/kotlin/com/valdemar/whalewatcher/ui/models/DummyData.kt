package com.valdemar.whalewatcher.ui.models

data class DummyDockerImage(
    val namespace: String,
    val name: String,
    val description: String,
    val starCount: Int,
    val pullCount: String,
)

data class DummyCategory(
    val title: String,
    val images: List<DummyDockerImage>,
)

object DummyData {
    val favorites =
        DummyCategory(
            title = "Favorites",
            images =
                listOf(
                    DummyDockerImage("linuxserver", "plex", "Media server container", 1200, "500M+"),
                    DummyDockerImage("library", "ubuntu", "Official Ubuntu base image", 10000, "1B+"),
                    DummyDockerImage("library", "nginx", "Official build of Nginx", 18000, "1B+"),
                ),
        )

    val databases =
        DummyCategory(
            title = "Databases",
            images =
                listOf(
                    DummyDockerImage("library", "postgres", "PostgreSQL database", 12000, "1B+"),
                    DummyDockerImage("library", "mysql", "MySQL database", 15000, "1B+"),
                    DummyDockerImage("library", "redis", "Redis in-memory data structure store", 13000, "1B+"),
                    DummyDockerImage("library", "mongo", "MongoDB document database", 9500, "1B+"),
                    DummyDockerImage("library", "mariadb", "MariaDB database", 5200, "1B+"),
                    DummyDockerImage("library", "influxdb", "Time series database", 2100, "500M+"),
                    DummyDockerImage("library", "cassandra", "Apache Cassandra", 1800, "100M+"),
                    DummyDockerImage("library", "couchdb", "CouchDB database", 1100, "50M+"),
                    DummyDockerImage("library", "neo4j", "Graph database", 1500, "100M+"),
                    DummyDockerImage("library", "arangodb", "Multi-model database", 600, "10M+"),
                ),
        )

    val devops =
        DummyCategory(
            title = "DevOps & CI/CD",
            images =
                listOf(
                    DummyDockerImage("jenkins", "jenkins", "Jenkins CI/CD", 6000, "500M+"),
                    DummyDockerImage("gitlab", "gitlab-ce", "GitLab Community Edition", 4000, "100M+"),
                    DummyDockerImage("portainer", "portainer-ce", "Docker UI management", 8000, "1B+"),
                    DummyDockerImage("library", "sonarqube", "Code quality inspection", 3200, "100M+"),
                    DummyDockerImage("gitea", "gitea", "Self-hosted Git service", 1500, "50M+"),
                    DummyDockerImage("drone", "drone", "Drone CI", 800, "50M+"),
                    DummyDockerImage("library", "docker", "Docker in Docker", 5000, "1B+"),
                    DummyDockerImage("library", "registry", "Docker Registry", 4500, "1B+"),
                    DummyDockerImage("localstack", "localstack", "Local AWS cloud stack", 2000, "100M+"),
                    DummyDockerImage("hashicorp", "vault", "Secrets management", 2500, "100M+"),
                ),
        )

    val mediaServers =
        DummyCategory(
            title = "Media Server",
            images =
                listOf(
                    DummyDockerImage("linuxserver", "plex", "Media server container", 2500, "500M+"),
                    DummyDockerImage("linuxserver", "jellyfin", "Jellyfin media server", 1800, "100M+"),
                    DummyDockerImage("linuxserver", "radarr", "Movie collection manager", 1200, "100M+"),
                    DummyDockerImage("linuxserver", "sonarr", "TV show manager", 1400, "100M+"),
                    DummyDockerImage("linuxserver", "lidarr", "Music collection manager", 600, "50M+"),
                    DummyDockerImage("linuxserver", "readarr", "Book manager", 400, "10M+"),
                    DummyDockerImage("linuxserver", "tautulli", "Plex monitoring", 800, "50M+"),
                    DummyDockerImage("linuxserver", "bazarr", "Subtitle manager", 500, "50M+"),
                    DummyDockerImage("linuxserver", "qbittorrent", "BitTorrent client", 1100, "100M+"),
                    DummyDockerImage("linuxserver", "transmission", "Transmission client", 900, "100M+"),
                ),
        )

    val customLists =
        listOf(
            DummyCategory(
                "My Weekend Project",
                listOf(DummyDockerImage("louislam", "uptime-kuma", "Monitoring tool", 5400, "50M+")),
            ),
            DummyCategory(
                "Homelab Services",
                listOf(DummyDockerImage("portainer", "portainer-ce", "Management UI", 8000, "1B+")),
            ),
            DummyCategory("Work Tools", emptyList()),
        )

    val systemCategories = listOf(databases, devops, mediaServers)
}
