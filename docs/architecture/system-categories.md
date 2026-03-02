# System Categories

## Overview
WhaleWatcher provides users with a set of pre-filled, read-only "System Categories" out-of-the-box. These categories serve as handpicked lists of popular and essential Docker images, grouped by topic, to help users easily discover and save useful containers without having to search for them manually.

## Characteristics of System Categories
- **Read-Only:** Users cannot add images to, or remove images from, a System Category.
- **Pre-filled:** The local database is seeded with these exact categories and their associated images upon installation or initial setup.
- **Unified Structure:** Under the hood, System Categories share the exact same data structure (the `List` entity) as user-created "Custom Lists" and the "Favorites" list, distinguished only by a type flag in the database schema.

## The 5 Core Categories and Their Contents

### 1. Databases
A collection of the most widely used relational and NoSQL database images. Essential starting points for backend services and full-stack deployments.
- `postgres`
- `mysql`
- `redis`
- `mongo`
- `mariadb`
- `influxdb`
- `cassandra`
- `couchdb`
- `neo4j`
- `arangodb`

### 2. DevOps & CI/CD
Tools required for continuous integration, deployment, and infrastructure management. Centralized tools for automating and managing the software lifecycle.
- `jenkins/jenkins`
- `gitlab/gitlab-ce`
- `portainer/portainer-ce`
- `sonarqube`
- `gitea/gitea`
- `drone/drone`
- `docker`
- `registry`
- `localstack/localstack`
- `hashicorp/vault`

### 3. Media Server
Popular self-hosted media and entertainment solutions. Targeted primarily at homelab enthusiasts looking to manage personal media collections.
- `linuxserver/plex`
- `linuxserver/jellyfin`
- `linuxserver/radarr`
- `linuxserver/sonarr`
- `linuxserver/lidarr`
- `linuxserver/readarr`
- `linuxserver/tautulli`
- `linuxserver/bazarr`
- `linuxserver/qbittorrent`
- `linuxserver/transmission`

### 4. Web Servers
Foundational web server and reverse proxy images. Core networking infrastructure for exposing internal services securely.
- `nginx`
- `httpd`
- `traefik`
- `caddy`
- `haproxy`
- `ghost`
- `linuxserver/swag`
- `nginx/nginx-ingress`
- `bitnami/nginx`
- `bitnami/apache`

### 5. Operating Systems
Base images used as the starting point for custom Dockerfiles or quick interactive shell environments. The building blocks for nearly all custom containerized applications.
- `ubuntu`
- `alpine`
- `debian`
- `centos`
- `fedora`
- `busybox`
- `amazonlinux`
- `rockylinux`
- `oraclelinux`
- `almalinux`