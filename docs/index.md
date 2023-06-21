# Milestones
- [ ] Create tables, people, workshop, workshop_people and instructor people
- [ ] Import data since 2020
- 


# Functional Requirements
- capture people (people table)
- capture worshops (workshop table)
- capture people per workshop (workshop_people table)
- capture instructors (select from people table)
- create table for 'school' and populate
  1. computing
  2. engineering
  3. maths, stats and physics

- create and populate 'programme' table
  1. MRes Physics (Theoretical) Full Time
  2. PhD Civil Engineering (full time)
  3. PhD Computer Science (full time)
  4. PhD Electrical and Electronic Engineering (full time)
  5. PhD Marine Technology (full time)
  6. PhD Mathematics (full time)
  7. PhD Physics (FT) (Theoretical)
    (blank)



# Non-functional requirements
- sqlite

# Conventions

- [Hungarian notation](https://en.wikipedia.org/wiki/Hungarian_notation#Examples)
- Private member
    - Default - m_camelCase
    - Static - s_camelCase
    - Constant - m_UPPER_CASE
    - Static constant - s_UPPER_CASE
- Public member
    - Default - camelCase
    - Constant - UPPER_CASE
- Avoid public members where possible (prefer getters/setters)
- Prefer Path over File and File over String
- Single instance classes (like CRMjServer/CRMjProperties) should always be final (to avoid accidental overwrites of references)

