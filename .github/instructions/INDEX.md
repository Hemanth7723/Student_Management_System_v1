# Student Management System v1 — Claude Project Assets

Generated for: https://github.com/Hemanth7723/Student_Management_System_v1

---

## 📋 Instructions

| File | Purpose |
|---|---|
| `instructions/PROJECT_INSTRUCTIONS.md` | Master reference: architecture, API, startup, Docker, deployment, coding standards |

---

## 🛠️ Skills

Skills tell Claude *how* to help with specific parts of the project.

| Skill | File | Covers |
|---|---|---|
| `student-service-dev` | `skills/student-service-dev/SKILL.md` | Java entities, DTOs, controllers, services, repositories, tests |
| `sms-infrastructure` | `skills/sms-infrastructure/SKILL.md` | Eureka, API Gateway, Docker, MySQL switch, Render deployment |
| `sms-frontend` | `skills/sms-frontend/SKILL.md` | HTML UI, JS fetch patterns, CSS, UX, CORS |

---

## 🤖 Agents

Agents are specialized personas Claude adopts for focused work.

| Agent | File | Role |
|---|---|---|
| Orchestrator | `agents/orchestrator.md` | Routes tasks, coordinates multi-agent work, project-level decisions |
| Backend Developer | `agents/backend-developer.md` | Spring Boot, JPA, REST API, tests |
| Infrastructure Engineer | `agents/infra-devops.md` | Eureka, Gateway, Docker, Render, MySQL |
| Frontend Developer | `agents/frontend-developer.md` | HTML/CSS/JS, API consumption, UX |

---

## How to Use

1. **Drop `PROJECT_INSTRUCTIONS.md`** into your Claude Project as the system prompt or project knowledge base
2. **Install skills** by uploading the `.skill` files to your Claude Project's skills section
3. **Invoke an agent** by telling Claude: *"Act as the Backend Developer agent"* or *"Act as the Infrastructure Engineer"*
4. **Let the Orchestrator route** complex multi-layer requests automatically
