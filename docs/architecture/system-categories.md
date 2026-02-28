# System Categories

## Overview
WhaleWatcher provides users with a set of pre-filled, read-only "System Categories" out-of-the-box. These categories serve as handpicked lists of popular and essential Docker images, grouped by topic, to help users easily discover and save useful containers without having to search for them manually.

## Characteristics of System Categories
- **Read-Only:** Users cannot add images to, or remove images from, a System Category.
- **Pre-filled:** The local database is seeded with these categories and their associated images upon installation or initial setup.
- **Unified Structure:** Under the hood, System Categories share the exact same data structure (the `List` entity) as user-created "Custom Lists" and the "Favorites" list, distinguished only by a type flag in the database schema.

## The 5 Core Categories

### 1. Databases
A collection of the most widely used relational and NoSQL database images.
- **Examples:** `postgres`, `mysql`, `redis`, `mongo`, `mariadb`.
- **Purpose:** Essential starting points for backend services and full-stack deployments.

### 2. DevOps & CI/CD
Tools required for continuous integration, deployment, and infrastructure management.
- **Examples:** `jenkins/jenkins`, `gitlab/gitlab-ce`, `portainer/portainer-ce`, `sonarqube`.
- **Purpose:** Centralized tools for automating and managing the software lifecycle.

### 3. Media Server
Popular self-hosted media and entertainment solutions.
- **Examples:** `linuxserver/plex`, `linuxserver/jellyfin`, `linuxserver/radarr`.
- **Purpose:** Targeted primarily at homelab enthusiasts looking to manage personal media collections.

### 4. Web Servers
Foundational web server and reverse proxy images.
- **Examples:** `nginx`, `httpd` (Apache), `traefik`, `caddy`.
- **Purpose:** Core networking infrastructure for exposing internal services securely.

### 5. Operating Systems
Base images used as the starting point for custom Dockerfiles or quick interactive shell environments.
- **Examples:** `ubuntu`, `alpine`, `debian`, `alpine`.
- **Purpose:** The building blocks for nearly all custom containerized applications.