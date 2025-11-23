-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into track (id, name, description, community, icon) values (1, "Software & Application Engineering", "Building and maintaining applications and services", "Contribute to startup MVPs, open-source projects, or product incubators","Code2");
insert into track (id, name, description, community, icon) values (2, "Cloud, DevOps & Infrastructure", "Systems automation, scalability, and reliability", "Design cloud labs, support startup deployments, optimize environments","Cloud");
insert into track (id, name, description, community, icon) values (3, "Data, AI & Analytics", "Turning data into intelligence and automation", "Collaborate on AI-based projects, participate in data challenges, train and deploy ML models","Database");
insert into track (id, name, description, community, icon) values (4, "Cybersecurity & Compliance", "Ensuring systems, data, and users remain secure", "Conduct vulnerability audits, lead security workshops, mentor startups on best practices","Shield");
insert into track (id, name, description, community, icon) values (5, "Architecture & IT Strategy", "Shaping large-scale design and technology direction", "Mentor project teams, review architectures, define incubator blueprints","Layers");
insert into track (id, name, description, community, icon) values (6, "Product, Design & UX", "Product usability, user experience, and customer value", "Collaborate with developers and startups to design user-centric products","Palette");
insert into track (id, name, description, community, icon) values (7, "IT Management & Leadership", "Driving execution, alignment, and delivery across teams", "Facilitate incubator programs, coordinate sprints, manage resources, and coach teams","Users");
insert into track (id, name, description, community, icon) values (8, "Business Technology & Digital Transformation", "At the intersection of business and technology", "Work with startups to translate business needs into tech solutions, validate market fits","Briefcase");
insert into track (id, name, description, community, icon) values (9, "Educators, Mentors & Community Contributors", "Sharing expertise and guiding others", "Support incubator programs, evaluate contributions, lead workshops or courses","GraduationCap");
insert into track (id, name, description, community, icon) values (10, "Emerging Tech & Research Specialists", "Working on new frontiers", "Lead exploratory projects, create PoCs, and contribute to innovation tracks","Sparkles"); 



insert into profile (id, name, description, skills) values (1, 'Frontend Developer (React, Angular, Vue)', 'Frontend Developer (React, Angular, Vue)', 'JavaScript, TypeScript, React, Angular, Vue.js, HTML, CSS'); 
insert into profile (id, name, description, skills) values (2, 'Backend Developer (Java, Python, Node.js, Go)', 'Backend Developer (Java, Python, Node.js, Go)', 'Java, Python, Node.js, Go, RESTful APIs, databases (SQL/NoSQL)');
insert into profile (id, name, description, skills) values (3, 'Full Stack Engineer', 'Full Stack Engineer', 'JavaScript, TypeScript, React, Node.js, databases (SQL/NoSQL), RESTful APIs'); 
insert into profile (id, name, description, skills) values (4, 'Mobile Developer (iOS / Android / Flutter)', 'Mobile Developer (iOS / Android / Flutter)', 'Swift, Kotlin, Flutter, React Native, mobile app development'); 
insert into profile (id, name, description, skills) values (5, 'Game Developer / XR Engineer', 'Game Developer / XR Engineer', 'Unity, Unreal Engine, C#, C++, AR/VR development');
insert into profile (id, name, description, skills) values (6, 'Software Engineer in Test (QA Automation)', 'Software Engineer in Test (QA Automation)', 'QA Automation, Selenium, JUnit, Cucumber');

insert into track_profiles (track_id, profile_id) values (1, 1);
insert into track_profiles (track_id, profile_id) values (1, 2);
insert into track_profiles (track_id, profile_id) values (1, 3);
insert into track_profiles (track_id, profile_id) values (1, 4);
insert into track_profiles (track_id, profile_id) values (1, 5);
insert into track_profiles (track_id, profile_id) values (1, 6);



insert into profile (id, name, description, skills) values (7, 'DevOps Engineer', 'DevOps Engineer', 'DevOps, GitOps, delivery pipelines, CI/CD, Kubernetes, Docker, Jenkins, Ansible, Terraform');
insert into profile (id, name, description, skills) values (8, 'Site Reliability Engineer (SRE)', 'Site Reliability Engineer (SRE)', 'automation, monitoring, incident response, SLIs/SLOs, Kubernetes, Prometheus, Grafana');
insert into profile (id, name, description, skills) values (9, 'Cloud Architect (AWS, Azure, GCP, OpenShift)', 'Cloud Architect (AWS, Azure, GCP, OpenShift)', 'cloud architecture, AWS, Azure, GCP, Kubernetes, OpenShift, microservices, ServiceMesh, Skupper, serverless');
insert into profile (id, name, description, skills) values (10, 'Systems Engineer / Administrator', 'Systems Engineer / Administrator', 'Linux, Windows Server, networking, virtualization, cloud platforms, scripting (Bash, Python)');
insert into profile (id, name, description, skills) values (11, 'Infrastructure as Code Specialist (Terraform, Ansible)', 'Infrastructure as Code Specialist (Terraform, Ansible)', 'Terraform, Ansible, cloud provisioning, configuration management, automation scripting');
insert into profile (id, name, description, skills) values (12, 'Network Engineer', 'Network Engineer', 'network design, routing, switching, firewalls, VPNs, network security, cloud networking');

insert into track_profiles (track_id, profile_id) values (2, 7);
insert into track_profiles (track_id, profile_id) values (2, 8);
insert into track_profiles (track_id, profile_id) values (2, 9);
insert into track_profiles (track_id, profile_id) values (2, 10);
insert into track_profiles (track_id, profile_id) values (2, 11);
insert into track_profiles (track_id, profile_id) values (2, 12);



insert into profile (id, name, description, skills) values (13, 'Data Engineer', 'Data Engineer', 'ETL, data pipelines, SQL, NoSQL, big data technologies (Hadoop, Spark), data warehousing');
insert into profile (id, name, description, skills) values (14, 'Data Scientist', 'Data Scientist', 'statistics, machine learning, Python/R, data visualization, big data tools (Hadoop, Spark)');
insert into profile (id, name, description, skills) values (15, 'Machine Learning Engineer', 'Machine Learning Engineer', 'machine learning, deep learning, Python, TensorFlow, PyTorch, model deployment');
insert into profile (id, name, description, skills) values (16, 'AI Researcher / Model Developer', 'AI Researcher / Model Developer', 'AI algorithms, research methodologies, Python, TensorFlow, PyTorch, model development');
insert into profile (id, name, description, skills) values (17, 'Business Intelligence Analyst', 'Business Intelligence Analyst', 'data analysis, SQL, data visualization tools (Tableau, Power BI), reporting');
insert into profile (id, name, description, skills) values (18, 'MLOps Engineer', 'MLOps Engineer', 'MLOps, model deployment, CI/CD for ML, Kubernetes, Docker, monitoring'); 

insert into track_profiles (track_id, profile_id) values (3, 13);
insert into track_profiles (track_id, profile_id) values (3, 14);
insert into track_profiles (track_id, profile_id) values (3, 15);
insert into track_profiles (track_id, profile_id) values (3, 16);
insert into track_profiles (track_id, profile_id) values (3, 17);
insert into track_profiles (track_id, profile_id) values (3, 18);


insert into profile (id, name, description, skills) values (19, 'Security Engineer', 'Security Engineer', 'vulnerability assessment, penetration testing, security tools, incident response');
insert into profile (id, name, description, skills) values (20, 'Ethical Hacker / Penetration Tester', 'Ethical Hacker / Penetration Tester', 'penetration testing, vulnerability assessment, security tools, reporting');
insert into profile (id, name, description, skills) values (21, 'SOC Analyst', 'SOC Analyst', 'security monitoring, incident response, SIEM tools, threat analysis');
insert into profile (id, name, description, skills) values (22, 'Security Architect', 'Security Architect', 'security architecture, risk management, compliance, security frameworks');
insert into profile (id, name, description, skills) values (23, 'GRC (Governance, Risk, Compliance) Specialist', 'GRC (Governance, Risk, Compliance) Specialist', 'governance, risk management, compliance frameworks, audits');
insert into profile (id, name, description, skills) values (24, 'DevSecOps Engineer', 'DevSecOps Engineer', 'DevSecOps, security automation, CI/CD security, Kubernetes security, container security');

insert into track_profiles (track_id, profile_id) values (4, 19);
insert into track_profiles (track_id, profile_id) values (4, 20);
insert into track_profiles (track_id, profile_id) values (4, 21);
insert into track_profiles (track_id, profile_id) values (4, 22);
insert into track_profiles (track_id, profile_id) values (4, 23);
insert into track_profiles (track_id, profile_id) values (4, 24);


insert into profile (id, name, description, skills) values (25, 'Solution Architect', 'Solution Architect', 'system design, architecture patterns, cloud platforms, microservices');
insert into profile (id, name, description, skills) values (26, 'Enterprise Architect', 'Enterprise Architect', 'enterprise architecture, TOGAF, system integration, technology strategy');
insert into profile (id, name, description, skills) values (27, 'Technical Product Owner', 'Technical Product Owner', 'product management, agile methodologies, stakeholder communication');
insert into profile (id, name, description, skills) values (28, 'Platform Architect', 'Platform Architect', 'platform design, cloud services, scalability, microservices architecture');
insert into profile (id, name, description, skills) values (29, 'Integration Specialist', 'Integration Specialist', 'system integration, APIs, middleware, data exchange protocols');

insert into track_profiles (track_id, profile_id) values (5, 25);
insert into track_profiles (track_id, profile_id) values (5, 26);
insert into track_profiles (track_id, profile_id) values (5, 27);
insert into track_profiles (track_id, profile_id) values (5, 28);
insert into track_profiles (track_id, profile_id) values (5, 29);


insert into profile (id, name, description, skills) values (30, 'Product Manager', 'Product Manager', 'product strategy, roadmap planning, stakeholder management');
insert into profile (id, name, description, skills) values (31, 'UX/UI Designer', 'UX/UI Designer', 'user experience design, user interface design, prototyping tools (Figma, Sketch)');
insert into profile (id, name, description, skills) values (32, 'Interaction Designer', 'Interaction Designer', 'interaction design, user research, prototyping, usability testing');
insert into profile (id, name, description, skills) values (33, 'User Researcher', 'User Researcher', 'user research methodologies, data analysis, usability testing');
insert into profile (id, name, description, skills) values (34, 'Design Systems Specialist', 'Design Systems Specialist', 'design systems, component libraries, UI frameworks');

insert into track_profiles (track_id, profile_id) values (6, 30);
insert into track_profiles (track_id, profile_id) values (6, 31);
insert into track_profiles (track_id, profile_id) values (6, 32);
insert into track_profiles (track_id, profile_id) values (6, 33);
insert into track_profiles (track_id, profile_id) values (6, 34);


insert into profile (id, name, description, skills) values (35, 'Project Manager / Scrum Master', 'Project Manager / Scrum Master', 'project management, agile methodologies, team facilitation');
insert into profile (id, name, description, skills) values (36, 'Delivery Manager', 'Delivery Manager', 'delivery management, stakeholder communication, risk management');
insert into profile (id, name, description, skills) values (37, 'IT Operations Manager', 'IT Operations Manager', 'IT operations, team leadership, process improvement');
insert into profile (id, name, description, skills) values (38, 'Technical Program Manager', 'Technical Program Manager', 'program management, technical leadership, cross-functional coordination');
insert into profile (id, name, description, skills) values (39, 'Engineering Manager', 'Engineering Manager', 'team leadership, software development processes, talent development');

insert into track_profiles (track_id, profile_id) values (7, 35);
insert into track_profiles (track_id, profile_id) values (7, 36);
insert into track_profiles (track_id, profile_id) values (7, 37);
insert into track_profiles (track_id, profile_id) values (7, 38);
insert into track_profiles (track_id, profile_id) values (7, 39);   


insert into profile (id, name, description, skills) values (40, 'Business Analyst', 'Business Analyst', 'requirements gathering, stakeholder communication, process modeling');
insert into profile (id, name, description, skills) values (41, 'IT Consultant', 'IT Consultant', 'IT strategy, technology assessment, stakeholder engagement');
insert into profile (id, name, description, skills) values (42, 'Digital Transformation Lead', 'Digital Transformation Lead', 'digital strategy, change management, technology adoption');
insert into profile (id, name, description, skills) values (43, 'ERP / CRM Specialist', 'ERP / CRM Specialist', 'ERP systems, CRM platforms, business process optimization');
insert into profile (id, name, description, skills) values (44, 'Cloud Adoption Consultant', 'Cloud Adoption Consultant', 'cloud strategy, migration planning, stakeholder engagement');

insert into track_profiles (track_id, profile_id) values (8, 40);
insert into track_profiles (track_id, profile_id) values (8, 41);
insert into track_profiles (track_id, profile_id) values (8, 42);
insert into track_profiles (track_id, profile_id) values (8, 43);
insert into track_profiles (track_id, profile_id) values (8, 44);


insert into profile (id, name, description, skills) values (45, 'Mentor / Coach', 'Mentor / Coach', 'mentoring, coaching, technical expertise');
insert into profile (id, name, description, skills) values (46, 'Technical Trainer', 'Technical Trainer', 'technical training, curriculum development, instructional design');
insert into profile (id, name, description, skills) values (47, 'Community Manager', 'Community Manager', 'community engagement, event planning, communication skills');
insert into profile (id, name, description, skills) values (48, 'Documentation Specialist', 'Documentation Specialist', 'technical writing, documentation tools, content management');
insert into profile (id, name, description, skills) values (49, 'Open Source Maintainer', 'Open Source Maintainer', 'open source contribution, project management, community engagement');

insert into track_profiles (track_id, profile_id) values (9, 45);
insert into track_profiles (track_id, profile_id) values (9, 46);
insert into track_profiles (track_id, profile_id) values (9, 47);
insert into track_profiles (track_id, profile_id) values (9, 48);
insert into track_profiles (track_id, profile_id) values (9, 49);


insert into profile (id, name, description, skills) values (50, 'Blockchain Developer', 'Blockchain Developer', 'blockchain platforms (Ethereum, Hyperledger), smart contracts, decentralized applications');
insert into profile (id, name, description, skills) values (51, 'IoT Engineer', 'IoT Engineer', 'IoT platforms, embedded systems, sensor networks, data protocols');
insert into profile (id, name, description, skills) values (52, 'AR/VR Specialist', 'AR/VR Specialist', 'augmented reality, virtual reality, Unity, Unreal Engine');
insert into profile (id, name, description, skills) values (53, 'Quantum Computing Researcher', 'Quantum Computing Researcher', 'quantum algorithms, quantum programming languages (Qiskit, Cirq), quantum hardware');
insert into profile (id, name, description, skills) values (54, 'Edge Computing Architect', 'Edge Computing Architect', 'edge computing, distributed systems, IoT integration, cloud-edge architectures');

insert into track_profiles (track_id, profile_id) values (10, 50);
insert into track_profiles (track_id, profile_id) values (10, 51);
insert into track_profiles (track_id, profile_id) values (10, 52);
insert into track_profiles (track_id, profile_id) values (10, 53);
insert into track_profiles (track_id, profile_id) values (10, 54);












-- alter sequence profile_seq restart with 4;
