-- Activities
insert into Activity (id, name, description) values (1, 'Learning Track', 'Learning Track');
insert into Activity (id, name, description) values (2, 'Bootcamp', 'Bootcamp');
insert into Activity (id, name, description) values (3, 'Hackathon', 'Hackathon');


-- Tracks and Profiles

insert into domain (id, name, description, community, icon) values (1, 'Software & Application Engineering', 'Building and maintaining applications and services', 'Contribute to startup MVPs, open-source projects, or product incubators','Code2');
insert into domain (id, name, description, community, icon) values (2, 'Cloud, DevOps & Infrastructure', 'Systems automation, scalability, and reliability', 'Design cloud labs, support startup deployments, optimize environments','Cloud');
insert into domain (id, name, description, community, icon) values (3, 'Data, AI & Analytics', 'Turning data into intelligence and automation', 'Collaborate on AI-based projects, participate in data challenges, train and deploy ML models','Database');
insert into domain (id, name, description, community, icon) values (4, 'Cybersecurity & Compliance', 'Ensuring systems, data, and users remain secure', 'Conduct vulnerability audits, lead security workshops, mentor startups on best practices','Shield');
insert into domain (id, name, description, community, icon) values (5, 'Architecture & IT Strategy', 'Shaping large-scale design and technology direction', 'Mentor project teams, review architectures, define incubator blueprints','Layers');
insert into domain (id, name, description, community, icon) values (6, 'Product, Design & UX', 'Product usability, user experience, and customer value', 'Collaborate with developers and startups to design user-centric products','Palette');
insert into domain (id, name, description, community, icon) values (7, 'IT Management & Leadership', 'Driving execution, alignment, and delivery across teams', 'Facilitate incubator programs, coordinate sprints, manage resources, and coach teams','Users');
insert into domain (id, name, description, community, icon) values (8, 'Business Technology & Digital Transformation', 'At the intersection of business and technology', 'Work with startups to translate business needs into tech solutions, validate market fits','Briefcase');
insert into domain (id, name, description, community, icon) values (9, 'Educators, Mentors & Community Contributors', 'Sharing expertise and guiding others', 'Support incubator programs, evaluate contributions, lead workshops or courses','GraduationCap');
insert into domain (id, name, description, community, icon) values (10, 'Emerging Tech & Research Specialists', 'Working on new frontiers', 'Lead exploratory projects, create PoCs, and contribute to innovation tracks','Sparkles'); 



insert into profile (id, name, description, skills) values (1, 'Frontend Developer', 'Frontend Developer (React, Angular, Nextjs, Vue)', 'JavaScript, TypeScript, React, Angular, Next.js, Vue.js, HTML, CSS, Bootstrap'); 
insert into profile (id, name, description, skills) values (2, 'Backend Developer', 'Backend Developer (Java, Python, Node.js, Go)', 'Java, Python, Node.js, Go, RESTful APIs, databases (SQL/NoSQL)');
insert into profile (id, name, description, skills) values (3, 'Full Stack Engineer', 'Full Stack Engineer', 'JavaScript, TypeScript, React, Node.js, databases (SQL/NoSQL), RESTful APIs'); 
insert into profile (id, name, description, skills) values (4, 'Mobile Developer', 'Mobile Developer (iOS / Android / Flutter)', 'Swift, Kotlin, Flutter, React Native, mobile app development'); 
insert into profile (id, name, description, skills) values (5, 'Game Developer / XR Engineer', 'Game Developer / XR Engineer', 'Unity, Unreal Engine, C#, C++, AR/VR development');
insert into profile (id, name, description, skills) values (6, 'Software Engineer in Test', 'Software Engineer in Test (QA Automation)', 'QA Automation, Selenium, JUnit, Cucumber');

insert into domain_profiles (domain_id, profile_id) values (1, 1);
insert into domain_profiles (domain_id, profile_id) values (1, 2);
insert into domain_profiles (domain_id, profile_id) values (1, 3);
insert into domain_profiles (domain_id, profile_id) values (1, 4);
insert into domain_profiles (domain_id, profile_id) values (1, 5);
insert into domain_profiles (domain_id, profile_id) values (1, 6);



insert into profile (id, name, description, skills) values (7, 'DevOps Engineer', 'DevOps Engineer', 'DevOps, GitOps, delivery pipelines, CI/CD, Kubernetes, Docker, Jenkins, Ansible, Terraform');
insert into profile (id, name, description, skills) values (8, 'Site Reliability Engineer (SRE)', 'Site Reliability Engineer (SRE)', 'automation, monitoring, incident response, SLIs/SLOs, Kubernetes, Prometheus, Grafana');
insert into profile (id, name, description, skills) values (9, 'Cloud Architect (AWS, Azure, GCP, OpenShift)', 'Cloud Architect (AWS, Azure, GCP, OpenShift)', 'cloud architecture, AWS, Azure, GCP, Kubernetes, OpenShift, microservices, ServiceMesh, Skupper, serverless');
insert into profile (id, name, description, skills) values (10, 'Systems Engineer / Administrator', 'Systems Engineer / Administrator', 'Linux, Windows Server, networking, virtualization, cloud platforms, scripting (Bash, Python)');
insert into profile (id, name, description, skills) values (11, 'Infrastructure as Code Specialist (Terraform, Ansible)', 'Infrastructure as Code Specialist (Terraform, Ansible)', 'Terraform, Ansible, cloud provisioning, configuration management, automation scripting');
insert into profile (id, name, description, skills) values (12, 'Network Engineer', 'Network Engineer', 'network design, routing, switching, firewalls, VPNs, network security, cloud networking');

insert into domain_profiles (domain_id, profile_id) values (2, 7);
insert into domain_profiles (domain_id, profile_id) values (2, 8);
insert into domain_profiles (domain_id, profile_id) values (2, 9);
insert into domain_profiles (domain_id, profile_id) values (2, 10);
insert into domain_profiles (domain_id, profile_id) values (2, 11);
insert into domain_profiles (domain_id, profile_id) values (2, 12);



insert into profile (id, name, description, skills) values (13, 'Data Engineer', 'Data Engineer', 'ETL, data pipelines, SQL, NoSQL, big data technologies (Hadoop, Spark), data warehousing');
insert into profile (id, name, description, skills) values (14, 'Data Scientist', 'Data Scientist', 'statistics, machine learning, Python/R, data visualization, big data tools (Hadoop, Spark)');
insert into profile (id, name, description, skills) values (15, 'Machine Learning Engineer', 'Machine Learning Engineer', 'machine learning, deep learning, Python, TensorFlow, PyTorch, model deployment');
insert into profile (id, name, description, skills) values (16, 'AI Researcher / Model Developer', 'AI Researcher / Model Developer', 'AI algorithms, research methodologies, Python, TensorFlow, PyTorch, model development');
insert into profile (id, name, description, skills) values (17, 'Business Intelligence Analyst', 'Business Intelligence Analyst', 'data analysis, SQL, data visualization tools (Tableau, Power BI), reporting');
insert into profile (id, name, description, skills) values (18, 'MLOps Engineer', 'MLOps Engineer', 'MLOps, model deployment, CI/CD for ML, Kubernetes, Docker, monitoring'); 

insert into domain_profiles (domain_id, profile_id) values (3, 13);
insert into domain_profiles (domain_id, profile_id) values (3, 14);
insert into domain_profiles (domain_id, profile_id) values (3, 15);
insert into domain_profiles (domain_id, profile_id) values (3, 16);
insert into domain_profiles (domain_id, profile_id) values (3, 17);
insert into domain_profiles (domain_id, profile_id) values (3, 18);


insert into profile (id, name, description, skills) values (19, 'Security Engineer', 'Security Engineer', 'vulnerability assessment, penetration testing, security tools, incident response');
insert into profile (id, name, description, skills) values (20, 'Ethical Hacker / Penetration Tester', 'Ethical Hacker / Penetration Tester', 'penetration testing, vulnerability assessment, security tools, reporting');
insert into profile (id, name, description, skills) values (21, 'SOC Analyst', 'SOC Analyst', 'security monitoring, incident response, SIEM tools, threat analysis');
insert into profile (id, name, description, skills) values (22, 'Security Architect', 'Security Architect', 'security architecture, risk management, compliance, security frameworks');
insert into profile (id, name, description, skills) values (23, 'GRC (Governance, Risk, Compliance) Specialist', 'GRC (Governance, Risk, Compliance) Specialist', 'governance, risk management, compliance frameworks, audits');
insert into profile (id, name, description, skills) values (24, 'DevSecOps Engineer', 'DevSecOps Engineer', 'DevSecOps, security automation, CI/CD security, Kubernetes security, container security');

insert into domain_profiles (domain_id, profile_id) values (4, 19);
insert into domain_profiles (domain_id, profile_id) values (4, 20);
insert into domain_profiles (domain_id, profile_id) values (4, 21);
insert into domain_profiles (domain_id, profile_id) values (4, 22);
insert into domain_profiles (domain_id, profile_id) values (4, 23);
insert into domain_profiles (domain_id, profile_id) values (4, 24);


insert into profile (id, name, description, skills) values (25, 'Solution Architect', 'Solution Architect', 'system design, architecture patterns, cloud platforms, microservices');
insert into profile (id, name, description, skills) values (26, 'Enterprise Architect', 'Enterprise Architect', 'enterprise architecture, TOGAF, system integration, technology strategy');
insert into profile (id, name, description, skills) values (27, 'Technical Product Owner', 'Technical Product Owner', 'product management, agile methodologies, stakeholder communication');
insert into profile (id, name, description, skills) values (28, 'Platform Architect', 'Platform Architect', 'platform design, cloud services, scalability, microservices architecture');
insert into profile (id, name, description, skills) values (29, 'Integration Specialist', 'Integration Specialist', 'system integration, APIs, middleware, data exchange protocols');

insert into domain_profiles (domain_id, profile_id) values (5, 25);
insert into domain_profiles (domain_id, profile_id) values (5, 26);
insert into domain_profiles (domain_id, profile_id) values (5, 27);
insert into domain_profiles (domain_id, profile_id) values (5, 28);
insert into domain_profiles (domain_id, profile_id) values (5, 29);


insert into profile (id, name, description, skills) values (30, 'Product Manager', 'Product Manager', 'product strategy, roadmap planning, stakeholder management');
insert into profile (id, name, description, skills) values (31, 'UX/UI Designer', 'UX/UI Designer', 'user experience design, user interface design, prototyping tools (Figma, Sketch)');
insert into profile (id, name, description, skills) values (32, 'Interaction Designer', 'Interaction Designer', 'interaction design, user research, prototyping, usability testing');
insert into profile (id, name, description, skills) values (33, 'User Researcher', 'User Researcher', 'user research methodologies, data analysis, usability testing');
insert into profile (id, name, description, skills) values (34, 'Design Systems Specialist', 'Design Systems Specialist', 'design systems, component libraries, UI frameworks');

insert into domain_profiles (domain_id, profile_id) values (6, 30);
insert into domain_profiles (domain_id, profile_id) values (6, 31);
insert into domain_profiles (domain_id, profile_id) values (6, 32);
insert into domain_profiles (domain_id, profile_id) values (6, 33);
insert into domain_profiles (domain_id, profile_id) values (6, 34);


insert into profile (id, name, description, skills) values (35, 'Project Manager / Scrum Master', 'Project Manager / Scrum Master', 'project management, agile methodologies, team facilitation');
insert into profile (id, name, description, skills) values (36, 'Delivery Manager', 'Delivery Manager', 'delivery management, stakeholder communication, risk management');
insert into profile (id, name, description, skills) values (37, 'IT Operations Manager', 'IT Operations Manager', 'IT operations, team leadership, process improvement');
insert into profile (id, name, description, skills) values (38, 'Technical Program Manager', 'Technical Program Manager', 'program management, technical leadership, cross-functional coordination');
insert into profile (id, name, description, skills) values (39, 'Engineering Manager', 'Engineering Manager', 'team leadership, software development processes, talent development');

insert into domain_profiles (domain_id, profile_id) values (7, 35);
insert into domain_profiles (domain_id, profile_id) values (7, 36);
insert into domain_profiles (domain_id, profile_id) values (7, 37);
insert into domain_profiles (domain_id, profile_id) values (7, 38);
insert into domain_profiles (domain_id, profile_id) values (7, 39);   


insert into profile (id, name, description, skills) values (40, 'Business Analyst', 'Business Analyst', 'requirements gathering, stakeholder communication, process modeling');
insert into profile (id, name, description, skills) values (41, 'IT Consultant', 'IT Consultant', 'IT strategy, technology assessment, stakeholder engagement');
insert into profile (id, name, description, skills) values (42, 'Digital Transformation Lead', 'Digital Transformation Lead', 'digital strategy, change management, technology adoption');
insert into profile (id, name, description, skills) values (43, 'ERP / CRM Specialist', 'ERP / CRM Specialist', 'ERP systems, CRM platforms, business process optimization');
insert into profile (id, name, description, skills) values (44, 'Cloud Adoption Consultant', 'Cloud Adoption Consultant', 'cloud strategy, migration planning, stakeholder engagement');

insert into domain_profiles (domain_id, profile_id) values (8, 40);
insert into domain_profiles (domain_id, profile_id) values (8, 41);
insert into domain_profiles (domain_id, profile_id) values (8, 42);
insert into domain_profiles (domain_id, profile_id) values (8, 43);
insert into domain_profiles (domain_id, profile_id) values (8, 44);


insert into profile (id, name, description, skills) values (45, 'Mentor / Coach', 'Mentor / Coach', 'mentoring, coaching, technical expertise');
insert into profile (id, name, description, skills) values (46, 'Technical Trainer', 'Technical Trainer', 'technical training, curriculum development, instructional design');
insert into profile (id, name, description, skills) values (47, 'Community Manager', 'Community Manager', 'community engagement, event planning, communication skills');
insert into profile (id, name, description, skills) values (48, 'Documentation Specialist', 'Documentation Specialist', 'technical writing, documentation tools, content management');
insert into profile (id, name, description, skills) values (49, 'Open Source Maintainer', 'Open Source Maintainer', 'open source contribution, project management, community engagement');

insert into domain_profiles (domain_id, profile_id) values (9, 45);
insert into domain_profiles (domain_id, profile_id) values (9, 46);
insert into domain_profiles (domain_id, profile_id) values (9, 47);
insert into domain_profiles (domain_id, profile_id) values (9, 48);
insert into domain_profiles (domain_id, profile_id) values (9, 49);


insert into profile (id, name, description, skills) values (50, 'Blockchain Developer', 'Blockchain Developer', 'blockchain platforms (Ethereum, Hyperledger), smart contracts, decentralized applications');
insert into profile (id, name, description, skills) values (51, 'IoT Engineer', 'IoT Engineer', 'IoT platforms, embedded systems, sensor networks, data protocols');
insert into profile (id, name, description, skills) values (52, 'AR/VR Specialist', 'AR/VR Specialist', 'augmented reality, virtual reality, Unity, Unreal Engine');
insert into profile (id, name, description, skills) values (53, 'Quantum Computing Researcher', 'Quantum Computing Researcher', 'quantum algorithms, quantum programming languages (Qiskit, Cirq), quantum hardware');
insert into profile (id, name, description, skills) values (54, 'Edge Computing Architect', 'Edge Computing Architect', 'edge computing, distributed systems, IoT integration, cloud-edge architectures');

insert into domain_profiles (domain_id, profile_id) values (10, 50);
insert into domain_profiles (domain_id, profile_id) values (10, 51);
insert into domain_profiles (domain_id, profile_id) values (10, 52);
insert into domain_profiles (domain_id, profile_id) values (10, 53);
insert into domain_profiles (domain_id, profile_id) values (10, 54);



-- Journey, Stages, and Milestones

insert into journey (id, name, title, description) values (1, 'IT Professional','Your path from foundation to global impact through 5 progressive stages of learning, contribution, and leadership.', 'From foundation to global impact through 5 progressive stages of learning, contribution, and leadership.');
insert into stage (id, sequence, name, description, icon, journey_id) values (1,1, 'Foundation', 'Onboarding & Learning','BookOpen', 1);
insert into stage (id, sequence, name, description, icon, journey_id) values (2,2, 'Practice', 'Real Use Cases','Blocks',1);
insert into stage (id, sequence, name, description, icon, journey_id) values (3,3, 'Contribution', 'Projects & Open Source','GitPullRequest',1);
insert into stage (id, sequence, name, description, icon, journey_id) values (4,4, 'Growth', 'Mentorship & Leadership','Users',1);
insert into stage (id, sequence, name, description, icon, journey_id) values (5,5, 'Impact', 'Career & Global Influence','TrendingUp',1);

insert into milestone (id, sequence, required_points, name, description, stage_id) values  (1, 1, 1,   'Join the community, set up profile', 'Join the community, set up profile',1);
insert into milestone (id, sequence, required_points, name, description, stage_id, skippable) values (2, 2, 10, 'Enroll in bootcamps & hands-on labs', 'Enroll in bootcamps & hands-on labs',1,false);
insert into milestone_activity (milestone_id,Activity_id) values (2,1);
insert into milestone_activity (milestone_id,Activity_id) values (2,2);
insert into milestone_activity (milestone_id,Activity_id) values (2,3);
insert into milestone (id, sequence, required_points, name, description, stage_id, skippable) values (3, 3, 300, 'Work toward certifications', 'Work toward certifications',1,true);


insert into milestone (id, sequence, name, description, stage_id) values (4, 4, 'Join hackathons & coding challenges', 'Join hackathons & coding challenges',2);
insert into milestone (id, sequence, name, description, stage_id) values (5, 5, 'Work on customer use-case simulations', 'Work on customer use-case simulations',2);
insert into milestone (id, sequence, name, description, stage_id) values (6, 6, 'Start building GitHub portfolio', 'Start building GitHub portfolio',2);
insert into milestone (id, sequence, name, description, stage_id) values (7, 7, 'Contribute to open source projects', 'Contribute to open source projects',3);
insert into milestone (id, sequence, name, description, stage_id) values (8, 8, 'Join team incubators (cross-functional squads)', 'Join team incubators (cross-functional squads)',3);
insert into milestone (id, sequence, name, description, stage_id) values (9, 9, 'Build reputation via successful contributions', 'Build reputation via successful contributions',3);
insert into milestone (id, sequence, name, description, stage_id) values (10, 10, 'Become a mentor for new members', 'Become a mentor for new members',4);
insert into milestone (id, sequence, name, description, stage_id) values (11, 11, 'Publish blogs, give talks, advocate technologies', 'Publish blogs, give talks, advocate technologies',4);
insert into milestone (id, sequence, name, description, stage_id) values (12, 12, 'Guide teams & incubators', 'Guide teams & incubators',4);
insert into milestone (id, sequence, name, description, stage_id) values (13, 13, 'Land jobs or build startups', 'Land jobs or build startups',5);
insert into milestone (id, sequence, name, description, stage_id) values (14, 14, 'Launch impactful community projects', 'Launch impactful community projects',5);
insert into milestone (id, sequence, name, description, stage_id) values (15, 15, 'Scale contributions across industries', 'Scale contributions across industries',5);


insert into journey(id, name, title, description) values (2, 'Recruiter', 'Discover, invest in, and hire top talent while building your brand in the community through 5 strategic stages.', 'Discover, invest in, and hire top talent while building your brand in the community.');
insert into stage (id, sequence, name, description, icon, journey_id) values (6,1, 'Onboarding', 'Setup & Discovery','Building2',2);
insert into stage (id, sequence, name, description, icon, journey_id) values (7,2, 'Talent Discovery', 'Find Rising Stars','Search',2);
insert into stage (id, sequence, name, description, icon, journey_id) values (8,3, 'Community Investment', 'Sponsor & Fund','DollarSign',2);
insert into stage (id, sequence, name, description, icon, journey_id) values (9,4, 'Branding', 'Build Reputation','Award',2);
insert into stage (id, sequence, name, description, icon, journey_id) values (10,5, 'Hiring Outcomes', 'Measure Success','Briefcase',2);

insert into milestone (id, sequence, name, description, stage_id) values (16, 1, 'Create company & recruiter profiles', 'Create company & recruiter profiles',6);
insert into milestone (id, sequence, name, description, stage_id) values (17, 2, 'Select skill investment interests', 'Select skill investment interests',6);
insert into milestone (id, sequence, name, description, stage_id) values (18, 3, 'Get an overview of CV bank & talent pool', 'Get an overview of CV bank & talent pool',6);
insert into milestone (id, sequence, name, description, stage_id) values (19, 4, 'Explore CV bank with filters (skills, projects, certifications)', 'Explore CV bank with filters (skills, projects, certifications)',7);
insert into milestone (id, sequence, name, description, stage_id) values (20, 5, 'Use AI matching to find rising talent', 'Use AI matching to find rising talent',7);
insert into milestone (id, sequence, name, description, stage_id) values (21, 6, 'Shortlist candidates & build pipelines', 'Shortlist candidates & build pipelines',7);
insert into milestone (id, sequence, name, description, stage_id) values (22, 7, 'Sponsor incubators (AI, Cloud, Security, etc.)', 'Sponsor incubators (AI, Cloud, Security, etc.)',8);
insert into milestone (id, sequence, name, description, stage_id) values (23, 8, 'Fund hackathons and coding challenges', 'Fund hackathons and coding challenges',8);
insert into milestone (id, sequence, name, description, stage_id) values (24, 9, 'Submit skill investment/resource proposals', 'Submit skill investment/resource proposals',8);
insert into milestone (id, sequence, name, description, stage_id) values (25, 10, 'Arrange private hiring competitions', 'Arrange private hiring competitions',8);
insert into milestone (id, sequence, name, description, stage_id) values (26, 11, 'Host webinars, AMAs, and community events', 'Host webinars, AMAs, and community events',9);
insert into milestone (id, sequence, name, description, stage_id) values (27, 12, 'Gain visibility as a top recruiter', 'Gain visibility as a top recruiter',9);
insert into milestone (id, sequence, name, description, stage_id) values (28, 13, 'Share success stories of hires', 'Share success stories of hires',9);
insert into milestone (id, sequence, name, description, stage_id) values (29, 14, 'Hire top-performing developers', 'Hire top-performing developers',10);
insert into milestone (id, sequence, name, description, stage_id) values (30, 15, 'Track ROI of sponsored programs', 'Track ROI of sponsored programs',10);
insert into milestone (id, sequence, name, description, stage_id) values (31, 16, 'Establish long-term brand recognition in the community', 'Establish long-term brand recognition in the community',10);


insert into journey(id, name, title, description) values (3, 'Startup', 'Build your MVP, access talent, and scale your product with community support through 5 growth stages.', 'Build your MVP, access talent, and scale your product with community support and resources.');
insert into stage (id, sequence, name, description, icon, journey_id) values (11,1, 'Foundation', 'Onboarding & Discovery','Lightbulb',3);
insert into stage (id, sequence, name, description, icon, journey_id) values (12,2, 'Discovery', 'Talent Discovery','UserPlus',3);
insert into stage (id, sequence, name, description, icon, journey_id) values (13,3, 'Incubation', 'Product Development','Code',3);
insert into stage (id, sequence, name, description, icon, journey_id) values (14,4, 'Growth', 'Community Integration','Network',3);
insert into stage (id, sequence, name, description, icon, journey_id) values (15,5, 'Impact', 'Scale','Zap',3);

insert into milestone (id, sequence, name, description, stage_id) values (32, 1, 'Register as a startup in the community', 'Register as a startup in the community',11);
insert into milestone (id, sequence, name, description, stage_id) values (33, 2, 'Create a profile with vision, product idea, and current challenges', 'Create a profile with vision, product idea, and current challenges',11);
insert into milestone (id, sequence, name, description, stage_id) values (34, 3, 'Identify needed skills and technical gaps', 'Identify needed skills and technical gaps',11);
insert into milestone (id, sequence, name, description, stage_id) values (35, 4, 'Explore the developer CV bank and available talent pool', 'Explore the developer CV bank and available talent pool',11);
insert into milestone (id, sequence, name, description, stage_id) values (36, 5, 'Match with developers and mentors from the community', 'Match with developers and mentors from the community',12);
insert into milestone (id, sequence, name, description, stage_id) values (37, 6, 'Build small project teams for MVPs and proofs-of-concept', 'Build small project teams for MVPs and proofs-of-concept',12);
insert into milestone (id, sequence, name, description, stage_id) values (38, 7, 'Start collaborating on technical tasks and use cases', 'Start collaborating on technical tasks and use cases',12);
insert into milestone (id, sequence, name, description, stage_id) values (39, 8, 'Join the Startup Incubator Track', 'Join the Startup Incubator Track',13);
insert into milestone (id, sequence, name, description, stage_id) values (40, 9, 'Access infrastructure, labs, and architecture guidance', 'Access infrastructure, labs, and architecture guidance',13);
insert into milestone (id, sequence, name, description, stage_id) values (41, 10, 'Run hackathons to test concepts and attract contributors', 'Run hackathons to test concepts and attract contributors',13);
insert into milestone (id, sequence, name, description, stage_id) values (42, 11, 'Build and refine the first product (MVP)', 'Build and refine the first product (MVP)',13);
insert into milestone (id, sequence, name, description, stage_id) values (43, 12, 'Open source selected parts of the project for community adoption', 'Open source selected parts of the project for community adoption',14);
insert into milestone (id, sequence, name, description, stage_id) values (44, 13, 'Gain visibility through demo days, startup spotlight sessions, and blogs', 'Gain visibility through demo days, startup spotlight sessions, and blogs',14);
insert into milestone (id, sequence, name, description, stage_id) values (45, 14, 'Partner with recruiters to scale the team with specialized talent', 'Partner with recruiters to scale the team with specialized talent',14);
insert into milestone (id, sequence, name, description, stage_id) values (46, 15, 'Launch MVP to market with community support', 'Launch MVP to market with community support',15);
insert into milestone (id, sequence, name, description, stage_id) values (47, 16, 'Pitch to investors via community pitch events', 'Pitch to investors via community pitch events',15);
insert into milestone (id, sequence, name, description, stage_id) values (48, 17, 'Scale product and operations while maintaining talent pipeline', 'Scale product and operations while maintaining talent pipeline',15);



insert into itprofessional (id, userId, domain_id, stage_id, profile_id) values (1, 'waelibrahim2000@hotmail.com', 1, 2, 1);
insert into user_progress (id, user_id, milestone_id,nextmilestone_id, prevProgress_id, startdate, closedate, status, earned_points) values (
            1, 1, 1, 2, null, CURRENT_DATE-10, CURRENT_DATE-8, 'Completed', 1);
--insert into user_progress (id, user_id, milestone_id,nextmilestone_id, prevProgress_id, startdate, closedate, status) values (
--            2, 1, 2, 3, 1, CURRENT_DATE-7, CURRENT_DATE-4, 'Completed');
--insert into user_progress (id, user_id, milestone_id,nextmilestone_id, prevProgress_id, startdate, closedate, status) values (
--            3, 1, 3, 4, 2, CURRENT_DATE-4, CURRENT_DATE-3, 'Completed');
--insert into user_progress (id, user_id, milestone_id,nextmilestone_id, prevProgress_id, startdate, closedate, status) values (
--            4, 1, 4, 5, 3, CURRENT_DATE-3, null, 'In-Progress');
insert into user_profiles (user_id,profile_id) values (1,2);
insert into user_profiles (user_id,profile_id) values (1,3);


insert into itprofessional (id, userId, domain_id, stage_id, profile_id) values (2, 'test@test.com', 2, 2, 2);

insert into team (name, admin_user_id) values ('Alpha Team', 1);
insert into team_members (team_id, user_id) values (1,1);
insert into team_members (team_id, user_id) values (1,2);




insert into badge (name, title, icon, trigger, rarity ) values ('Member','✅ Member', 'User', 'Registeration','common');
insert into badge (name, title, icon, trigger, rarity ) values ('Active Member','💬 Active Member', 'Activity', '10+ posts/comments','rare');
insert into badge (name, title, icon, trigger, rarity ) values ('Learner', '🟦 Learner','GraduationCap', 'Completing 3 onboarding milestones','epic');

insert into badge (name, title, icon, trigger, rarity ) values ('Helper','🤝 Helper', 'MessageSquare', 'Help 5 members','legendary');
insert into badge (name, title, icon, trigger, rarity ) values ('Mentor','🧑‍🏫 Mentor', 'Award', 'Provide verified mentoring','epic');
insert into badge (name, title, icon, trigger, rarity ) values ('Team Player','🤜🤛 Team Player', 'Users', 'Participate in hackathon squad','rare');


insert into badge (name, title, icon, trigger, rarity ) values ('Practitioner', '🟩 Practitioner', 'Wrench', 'Completing real use-case tasks','epic');
insert into badge (name, title, icon, trigger ) values ('Contributor','🟧 Contributor','Code-fork','Completing OSS or internal projects');
insert into badge (name, title, icon, trigger ) values ('Leader','🟨 Leader','Crown','Community influence, job impact');

insert into badge (name, title, icon, trigger ) values ('Streak Master','🔥 Streak Master','Flame','7 days or 30 days active learning');
insert into badge (name, title, icon, trigger ) values ('Fast Climber','🏅 Fast Climber','Zap','Reach Stage 3 within 60 days');
insert into badge (name, title, icon, trigger ) values ('Super Contributor','🚀 Super Contributor','','Publish 10+ contributions');
insert into badge (name, title, icon, trigger ) values ('Top 5% Performer','🌟 Top 5% Performer','','Based on journey benchmarking');

insert into user_badges (user_id,badge_id,awardedAt) values (1,1,'1/4/2025');
insert into user_badges (user_id,badge_id,awardedAt) values (1,2,'5/15/2025');
insert into user_badges (user_id,badge_id,awardedAt) values (1,3,'6/11/2025');
insert into user_badges (user_id,badge_id,awardedAt) values (1,4,'7/24/2025');
insert into user_badges (user_id,badge_id,awardedAt) values (1,5,'8/01/2025');
insert into user_badges (user_id,badge_id,awardedAt) values (1,6,'9/13/2025');
insert into user_badges (user_id,badge_id,awardedAt) values (1,7,'10/02/2025');

 
alter sequence Activity_id_seq restart with 20;
alter sequence domain_id_seq restart with 20;
alter sequence journey_id_seq restart with 20;
alter sequence stage_id_seq restart with 20;
alter sequence milestone_id_seq restart with 20; 
alter sequence user_progress_id_seq restart with 20; 
