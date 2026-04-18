# Student Management System v1 — Claude Project Assets
### Complete Agent & Skill Registry

Generated for: https://github.com/Hemanth7723/Student_Management_System_v1

---

## 📋 Instructions

| File | Purpose |
|---|---|
| `instructions/PROJECT_INSTRUCTIONS.md` | Master reference: architecture, API, startup, Docker, deployment, coding standards |

---

## 🛠️ Skills

| Skill | File | Covers |
|---|---|---|
| `student-service-dev` | `skills/student-service-dev/SKILL.md` | Java entities, DTOs, controllers, services, repositories, tests |
| `sms-infrastructure` | `skills/sms-infrastructure/SKILL.md` | Eureka, API Gateway, Docker, MySQL, Render deployment |
| `sms-frontend` | `skills/sms-frontend/SKILL.md` | HTML UI, JS fetch patterns, CSS, UX, CORS |

---

## 🤖 Agents — Full Registry

### 🧠 Coordination

| Agent | File | Role |
|---|---|---|
| **Master Agent** | `agents/master-agent.md` | Single point of contact. Classifies tasks, assigns agents, tracks progress, escalates blockers, owns PR lifecycle |

### 👷 Development

| Agent | File | Role |
|---|---|---|
| **Backend Developer** | `agents/backend-developer.md` | Java, Spring Boot 3, JPA, REST API, unit/integration tests |
| **Infrastructure Engineer** | `agents/infra-devops.md` | Eureka, API Gateway, Docker/Compose, MySQL switch, Render deploy |
| **Frontend Developer** | `agents/frontend-developer.md` | HTML/CSS/JS, API consumption, UX, CORS |

### 🔍 Review & Approval

| Agent | File | Role |
|---|---|---|
| **Code Reviewer** | `agents/code-reviewer.md` | Reviews all code changes across all layers. Surfaces blocking issues before security scans |
| **PR Approver** | `agents/pr-approver.md` | Final merge gate. Collects all review + security reports and makes approve/block decision |

### 🔐 Security

| Agent | File | Tool | Checks |
|---|---|---|---|
| **FOSSA Agent** | `agents/security-fossa.md` | FOSSA | License compliance, copyleft risk, dependency policy |
| **SonarQube Agent** | `agents/security-sonarqube.md` | SonarQube | Code quality, bugs, vulnerabilities, coverage, code smells |
| **42Crunch Agent** | `agents/security-42crunch.md` | 42Crunch | API security audit, OpenAPI spec validation, OWASP API Top 10 |
| **Cycode SAST Agent** | `agents/security-cycode-sast.md` | Cycode SAST | Source code vulnerabilities, taint analysis, injection flaws |
| **Cycode Secrets Agent** | `agents/security-cycode-secrets.md` | Cycode Secrets | Hardcoded credentials, API keys, tokens, secrets in git history |

---

## 🔄 Standard PR Pipeline

```
Request → Master Agent
              │
              ▼
     Development Agent(s)
     (Backend / Infra / Frontend)
              │
              ▼
        Code Reviewer
              │ PASS
              ▼
   ┌──────────────────────────┐
   │    Security Scans        │  (run in parallel)
   │  FOSSA | SonarQube       │
   │  42Crunch | SAST | Secrets│
   └──────────────────────────┘
              │ ALL PASS
              ▼
         PR Approver
         (Merge / Block)
```

---

## How to Invoke Agents

Tell Claude which agent to act as:

- *"Act as the Master Agent — I want to add a phone number field to students"*
- *"Act as the Code Reviewer — review this Java controller"*
- *"Act as the Cycode Secrets Agent — scan this properties file"*
- *"Act as the PR Approver — here are all the gate reports"*
- *"Act as the SonarQube Agent — analyze this service class"*
