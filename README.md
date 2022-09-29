## EAD - APi Rest de microserviços de integração de usuários e cursos.

Desenvolvido para teste de tecnologias do ecosistema spring padrões de microserviços.

* service-registry: Responsável por registrar todos os microserviços.
* config-server: Responsável por guardar as configurações gerais de todos microserviços.
* api-gateway: Responsável pela mapeamento e direcionamento das requisições para as instâncias dos microserviços.
* authuser: Microserviço para registro e usuários.
* course: Microserviço integrado com authuser através de rabbitMQ
* notification: Microserviço responsável pelas notificações de usários.
* notification-hex: Mesmo funcionalidade do notification mais desenvolvido em um arquitetura hexagonal.
