-- SCRIPT INFORMATION --
-- Types: mysql mariadb
-- Version: 1
-- Upgrades: 0
-- SCRIPT INFORMATION --

START TRANSACTION;
SET foreign_key_checks = 0;

DROP TABLE IF EXISTS banda_player_log CASCADE;
DROP TABLE IF EXISTS banda_bans CASCADE;
DROP TABLE IF EXISTS banda_banned_player CASCADE;
DROP TABLE IF EXISTS banda_banned_ip CASCADE;
DROP TABLE IF EXISTS banda_protection CASCADE;
DROP TABLE IF EXISTS banda_banned_ip_range CASCADE;


CREATE TABLE banda_player_log (
  player_uuid  BINARY(16)  NOT NULL,
  player_name  VARCHAR(32) NOT NULL,
  ip           BINARY(4)   NOT NULL,
  log_time     TIMESTAMP   NOT NULL            DEFAULT CURRENT_TIMESTAMP,
  event_result INTEGER     NOT NULL
);

CREATE INDEX banda_ip_uuid_idx ON banda_player_log (player_uuid, ip);

CREATE TABLE banda_bans (
  ban_id      BIGINT       NOT NULL            AUTO_INCREMENT,
  issuer_uuid BINARY(16)   NOT NULL,
  reason      VARCHAR(255) NOT NULL,
  issued      TIMESTAMP    NOT NULL            DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ban_id)
);

CREATE TABLE banda_banned_player (
  ban_id      BIGINT     NOT NULL,
  player_uuid BINARY(16) NOT NULL,
  PRIMARY KEY (player_uuid),
  FOREIGN KEY (ban_id) REFERENCES banda_bans (ban_id)
    ON DELETE CASCADE
);

CREATE TABLE banda_banned_ip (
  ban_id BIGINT        NOT NULL,
  ip     VARBINARY(16) NOT NULL,
  --    netmask                                 VARBINARY(16)   NOT NULL,
  PRIMARY KEY (ban_id),
  FOREIGN KEY (ban_id) REFERENCES banda_bans (ban_id)
    ON DELETE CASCADE,
  UNIQUE (ip)
);

CREATE TABLE banda_banned_ip_range (
  ban_id  BIGINT        NOT NULL,
  ip      VARBINARY(16) NOT NULL,
  netmask VARBINARY(16) NOT NULL,
  PRIMARY KEY (ban_id),
  FOREIGN KEY (ban_id) REFERENCES banda_bans (ban_id)
    ON DELETE CASCADE,
  UNIQUE (ip, netmask)
);


CREATE TABLE banda_protection (
  player_uuid              BINARY(16) NOT NULL,
  protect_ip_range_ban     INTEGER    NOT NULL,
  protect_ip_ban           INTEGER    NOT NULL,
  protect_multiaccount_ban INTEGER    NOT NULL,
  PRIMARY KEY (player_uuid)
);

SET foreign_key_checks = 1;
COMMIT;