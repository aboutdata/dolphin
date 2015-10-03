/*
USE globalid_db;

DROP TABLE IF EXISTS BLOCK_IP;
DROP TABLE IF EXISTS REFRESH_TOKEN;
DROP TABLE IF EXISTS ACCESS_TOKEN;
DROP TABLE IF EXISTS AUTHORIZATION;
DROP TABLE IF EXISTS EMAIL_VERIFICATION;
DROP TABLE IF EXISTS PERMISSION;
DROP TABLE IF EXISTS AUTHENTICATION;
DROP TABLE IF EXISTS PROFILE;
DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS APPLICATION;
DROP TABLE IF EXISTS USER_GROUP;
*/

CREATE DATABASE IF NOT EXISTS globalid_db;
USE globalid_db;

/* ********************************************************************************************* */
/* 전체 Common 정보 테이블 시작 */
DROP TABLE IF EXISTS USER_GROUP;
CREATE TABLE USER_GROUP (
	GROUP_KEY VARCHAR(36) NOT NULL COMMENT 'Group key',
	GROUP_NAME VARCHAR(32) NOT NULL COMMENT 'Group name',
	MODIFY_DATE DATETIME NOT NULL COMMENT 'Last modification date',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (GROUP_KEY)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'User group info table';
/* 전체 Common 정보 테이블 끝 */
/* ********************************************************************************************* */

/* ********************************************************************************************* */
/* User 기본 정보 테이블 시작 */
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
	USER_SEQ BIGINT(14) NOT NULL AUTO_INCREMENT COMMENT '유저키 생성 값',
	USER_KEY VARCHAR(36) NOT NULL COMMENT 'User key',
	GROUP_KEY VARCHAR(36) NOT NULL COMMENT 'Group key',
	EMAIL_ID VARCHAR(128) NOT NULL COMMENT 'Email format id',
	MODIFY_DATE DATETIME NOT NULL COMMENT 'Last modification date',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (USER_SEQ),	
	UNIQUE KEY USER_IDX01 (USER_KEY),
	UNIQUE KEY USER_IDX02 (GROUP_KEY, EMAIL_ID),
	KEY USER_IDX03 (EMAIL_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'User table';
ALTER TABLE USER AUTO_INCREMENT = 1000000001;
/* USER_SEQ는 10자리 1000000001 부터 사용됨 */
/* User 기본 정보 테이블 끝 */
/* ********************************************************************************************* */

/* ********************************************************************************************* */
/* PROFILE 기본 정보 테이블 시작 */
DROP TABLE IF EXISTS PROFILE;
CREATE TABLE PROFILE (
	USER_SEQ BIGINT(14) NOT NULL COMMENT 'User seq',
	FIRST_NAME VARCHAR(32) NULL COMMENT 'First name(nick name)',
	LAST_NAME VARCHAR(32) NULL COMMENT 'Last name',
	BIRTHDAY VARCHAR(8) NULL COMMENT 'Birthday(yyyyMMdd format)',
	PHONE VARCHAR(32) NULL COMMENT 'Phone number only numeric',		
	GENDER VARCHAR(1) NULL COMMENT 'Gender M/F',
	MODIFY_DATE DATETIME NOT NULL COMMENT 'Last modification date',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (USER_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'User table';
/* PROFILE 기본 정보 테이블 끝 */
/* ********************************************************************************************* */


/* ********************************************************************************************* */
/* Application 기본 정보 테이블 시작 */
DROP TABLE IF EXISTS APPLICATION;
CREATE TABLE APPLICATION (
	CLIENT_ID VARCHAR(36) NOT NULL COMMENT 'Client id',
	CLIENT_SECRET VARCHAR(128) NOT NULL COMMENT 'Client secret',
	APP_NAME VARCHAR(32) NOT NULL COMMENT 'App name',
	GROUP_KEY VARCHAR(36) NOT NULL COMMENT 'Group key',
	MODIFY_DATE DATETIME NOT NULL COMMENT 'Last modification date',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (CLIENT_ID),
	KEY APPLICATION_IDX01 (GROUP_KEY)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Application info table';
/* Application 기본 정보 테이블 끝 */
/* ********************************************************************************************* */

/* ********************************************************************************************* */
/* 인증 정보 테이블 시작 */
DROP TABLE IF EXISTS AUTHENTICATION;
CREATE TABLE AUTHENTICATION (
	USER_SEQ BIGINT(14) NOT NULL COMMENT 'User seq',
	PASSWORD VARCHAR(128) NOT NULL COMMENT 'Password',
	AUTH_STATUS_CODE VARCHAR(2) NOT NULL COMMENT 'Login status code',
	AUTH_FAIL_COUNT INT NOT NULL COMMENT 'Login failure count',
	MODIFY_DATE DATETIME NOT NULL COMMENT 'Last modification date',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (USER_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Auth profile table';
/* 인증 정보 테이블 끝 */
/* ********************************************************************************************* */

/* ********************************************************************************************* */
/* 이용동의 정보 테이블 시작 */
DROP TABLE IF EXISTS PERMISSION;
CREATE TABLE PERMISSION (
	USER_SEQ BIGINT(14) NOT NULL COMMENT 'User seq',
	CLIENT_ID VARCHAR(36) NOT NULL COMMENT 'Client id',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (USER_SEQ, CLIENT_ID),
	KEY PERMISSION_IDX01 (USER_SEQ)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'permission table';
/* 권한 정보 테이블 끝 */
/* ********************************************************************************************* */

/* ********************************************************************************************* */
/* Email 확인 정보 테이블 시작 */
DROP TABLE IF EXISTS EMAIL_VERIFICATION;
CREATE TABLE EMAIL_VERIFICATION (
	USER_SEQ BIGINT(14) NOT NULL COMMENT 'User seq',
	VERIFICATION_VALUE VARCHAR(64) NOT NULL COMMENT 'Value for verification',
	CRC32 BIGINT NOT NULL COMMENT 'VERIFICATION_VALUE as crc32',
	VERIFIED_DATE DATETIME NULL COMMENT 'Verified date',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (USER_SEQ),
	KEY EMAIL_VERIFICATION_IDX01 (CRC32)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Email verification table';
/* Email 확인 정보 테이블 끝 */
/* ********************************************************************************************* */

/* ********************************************************************************************* */
/* 권한 정보 테이블 시작 */
DROP TABLE IF EXISTS AUTHORIZATION;
CREATE TABLE AUTHORIZATION (
	ID_SEQ BIGINT(14) NOT NULL AUTO_INCREMENT COMMENT 'Seq 생성 값',
	OWNER VARCHAR(36) NOT NULL COMMENT 'Client id or User key',
	GRANT_TYPE_CODE INT NOT NULL COMMENT '권한 부여 형식 코드',
	LEVEL INT NOT NULL COMMENT '권한 레벨',
	PRIMARY KEY (ID_SEQ),
	UNIQUE KEY AUTHORIZATION_IDX01 (OWNER)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'Authorization table';
ALTER TABLE AUTHORIZATION AUTO_INCREMENT = 10000001;
/* ID_SEQ 8자리 10000001 부터 사용됨 */
/* 권한 정보 테이블 끝 */

/* ACCESS_TOKEN 테이블 시작 */
DROP TABLE IF EXISTS ACCESS_TOKEN;
CREATE TABLE ACCESS_TOKEN (
	ID_SEQ BIGINT(14) NOT NULL COMMENT 'Seq',
	ACCESS_TOKEN VARCHAR(24) NOT NULL COMMENT 'AccessToken',
	EXPIRES_IN INT NOT NULL COMMENT '만료시간 초단위',
	REFRESH_DATE DATETIME NOT NULL COMMENT 'Refresh date',
	ISSUE_DATE DATETIME NOT NULL COMMENT 'Issue date',
	PRIMARY KEY (ID_SEQ),
	UNIQUE KEY ACCESS_TOKEN_IDX01 (ACCESS_TOKEN)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'ACCESS_TOKEN table';
/* ACCESS_TOKEN 테이블 끝 */

/* refresh token 테이블 시작 */
DROP TABLE IF EXISTS REFRESH_TOKEN;
CREATE TABLE REFRESH_TOKEN (
	ID_SEQ BIGINT(14) NOT NULL COMMENT 'Seq',
	REFRESH_TOKEN VARCHAR(24) NOT NULL COMMENT 'RefreshToken',
	EXPIRES_IN INT NOT NULL COMMENT '만료시간 초단위',
	PRIMARY KEY (ID_SEQ),
	UNIQUE KEY REFRESH_TOKEN_IDX01 (REFRESH_TOKEN)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'REFRESH_TOKEN table';
/* refresh token 테이블 끝 */
/* ********************************************************************************************* */

/* ********************************************************************************************* */
/* block ip 테이블 시작 */
DROP TABLE IF EXISTS BLOCK_IP;
CREATE TABLE BLOCK_IP (
	IP VARCHAR(18) NOT NULL COMMENT 'ipv4 ip',
	REGIST_DATE DATETIME NOT NULL COMMENT 'Regist date',
	PRIMARY KEY (IP)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'BLOCK_IP table';
/* block ip 테이블 끝 */
/* ********************************************************************************************* */