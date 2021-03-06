
INSERT INTO `TBCA_CD` (`CD_CLSFCTN`, `CD`, `CD_NM`, `SYS_CD_NM`, `BSISVAL_ALTV`, `SORT_ORDR`, `RM`, `USE_ALTV`, `DEL_ALTV`, `REGR_ID`, `REGN_DT`, `LST_UPDUSR_ID`, `LST_UPDT_DT`, `CHG_CNT`) VALUES
('AGRDE_SECD', '1', '20대 이하', '20대 이하', 0, 1, '', 1, 0, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO `TBCA_CD` (`CD_CLSFCTN`, `CD`, `CD_NM`, `SYS_CD_NM`, `BSISVAL_ALTV`, `SORT_ORDR`, `RM`, `USE_ALTV`, `DEL_ALTV`, `REGR_ID`, `REGN_DT`, `LST_UPDUSR_ID`, `LST_UPDT_DT`, `CHG_CNT`) VALUES
('AGRDE_SECD', '2', '20대 이하', '20대 이하', 0, 2, '', 1, 0, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO `TBCA_CD` (`CD_CLSFCTN`, `CD`, `CD_NM`, `SYS_CD_NM`, `BSISVAL_ALTV`, `SORT_ORDR`, `RM`, `USE_ALTV`, `DEL_ALTV`, `REGR_ID`, `REGN_DT`, `LST_UPDUSR_ID`, `LST_UPDT_DT`, `CHG_CNT`) VALUES
('AGRDE_SECD', '3', '30대', '30대', 0, 3, '', 1, 0, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO `TBCA_CD` (`CD_CLSFCTN`, `CD`, `CD_NM`, `SYS_CD_NM`, `BSISVAL_ALTV`, `SORT_ORDR`, `RM`, `USE_ALTV`, `DEL_ALTV`, `REGR_ID`, `REGN_DT`, `LST_UPDUSR_ID`, `LST_UPDT_DT`, `CHG_CNT`) VALUES
('AGRDE_SECD', '4', '40대', '40대', 0, 4, '', 1, 0, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO `TBCA_CD` (`CD_CLSFCTN`, `CD`, `CD_NM`, `SYS_CD_NM`, `BSISVAL_ALTV`, `SORT_ORDR`, `RM`, `USE_ALTV`, `DEL_ALTV`, `REGR_ID`, `REGN_DT`, `LST_UPDUSR_ID`, `LST_UPDT_DT`, `CHG_CNT`) VALUES
('AGRDE_SECD', '5', '50대', '50대', 0, 5, '', 1, 0, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO `TBCA_CD` (`CD_CLSFCTN`, `CD`, `CD_NM`, `SYS_CD_NM`, `BSISVAL_ALTV`, `SORT_ORDR`, `RM`, `USE_ALTV`, `DEL_ALTV`, `REGR_ID`, `REGN_DT`, `LST_UPDUSR_ID`, `LST_UPDT_DT`, `CHG_CNT`) VALUES
('AGRDE_SECD', '6', '60대', '60대', 0, 6, '', 1, 0, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 1);
INSERT INTO `TBCA_CD` (`CD_CLSFCTN`, `CD`, `CD_NM`, `SYS_CD_NM`, `BSISVAL_ALTV`, `SORT_ORDR`, `RM`, `USE_ALTV`, `DEL_ALTV`, `REGR_ID`, `REGN_DT`, `LST_UPDUSR_ID`, `LST_UPDT_DT`, `CHG_CNT`) VALUES
('AGRDE_SECD', '7', '70대 이상', '70대 이상', 0, 7, '', 1, 0, 0, CURRENT_TIMESTAMP, 0, CURRENT_TIMESTAMP, 1);
COMMIT;


1. TBCA_DLY_LDIN_STAT 일별_인입_현황
	INSERT INTO TBCA_DLY_LDIN_STAT
		(`DT`, 
		`CALL_CNT`, 
		`USER_CNT`, 
		`TR_CNT`, 
		ERR_CNT, 
		`RLTM_INQIRE_CNT`, 
		`RLTM_TRANSFR_CNT`, 
		`PRCS_DT`
		)
	SELECT CONCAT('2019', RIGHT(YMD, 4))
		, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CALL_CNT
		, LPAD(FLOOR(RAND()*1000000), 5, '1') AS USER_CNT
		, LPAD(FLOOR(RAND()*1000000), 5, '2') AS TR_CNT
		, LPAD(FLOOR(RAND()*1000000), 2, '1') AS ERR_CNT
		, LPAD(FLOOR(RAND()*1000000), 4, '1') AS RLTM_INQIRE_CNT
		, LPAD(FLOOR(RAND()*1000000), 4, '1') AS RLTM_TRANSFR_CNT
		, CURRENT_TIMESTAMP
	FROM TEST_YMDHM
	WHERE YMD <= '20180930'
	AND YMD > '20180924'
	GROUP BY YMD;
	COMMIT;
	
	2. TBCA_MLY_LDIN_STAT 분별_인입_현황
	INSERT INTO TBCA_MLY_LDIN_STAT
		(DT, 
		 HM,
		CALL_CNT, 
		USER_CNT, 
		TR_CNT, 
		ERR_CNT, 
		RLTM_INQIRE_CNT, 
		RLTM_TRANSFR_CNT, 
		PRCS_DT
		)
	SELECT YMD, HM
		, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CALL_CNT
		, LPAD(FLOOR(RAND()*1000000), 5, '1') AS USER_CNT
		, LPAD(FLOOR(RAND()*1000000), 5, '2') AS TR_CNT
		, LPAD(FLOOR(RAND()*1000000), 2, '1') AS ERR_CNT
		, LPAD(FLOOR(RAND()*1000000), 4, '1') AS RLTM_INQIRE_CNT
		, LPAD(FLOOR(RAND()*1000000), 4, '1') AS RLTM_TRANSFR_CNT
		, CURRENT_TIMESTAMP
	FROM TEST_YMDHM
	GROUP BY YMD, HM;
	COMMIT;
	
	INSERT INTO TBCA_MLY_LDIN_STAT
		(DT, 
		 HM,
		CALL_CNT, 
		USER_CNT, 
		TR_CNT, 
		RLTM_INQIRE_CNT, 
		RLTM_TRANSFR_CNT, 
		PRCS_DT
		)
	SELECT CONCAT('2019', RIGHT(YMD, 4)), HM
		, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CALL_CNT
		, LPAD(FLOOR(RAND()*1000000), 5, '1') AS USER_CNT
		, LPAD(FLOOR(RAND()*1000000), 5, '2') AS TR_CNT
		, LPAD(FLOOR(RAND()*1000000), 4, '1') AS RLTM_INQIRE_CNT
		, LPAD(FLOOR(RAND()*1000000), 4, '1') AS RLTM_TRANSFR_CNT
		, CURRENT_TIMESTAMP
	FROM TEST_YMDHM
	GROUP BY YMD, HM;
	COMMIT;

	3. TBCA_30MLY_TR_STAT 30분별_TR_현황
	INSERT INTO TBCA_30MLY_TR_STAT(DT, HM, CNT, PRCS_DT)
	SELECT CONCAT('2019', RIGHT(YMD, 4)), HM, LPAD(FLOOR(RAND()*1000000), 6, '1') AS CNT, CURRENT_TIMESTAMP
	FROM TEST_YMDHM 
	WHERE EXISTS(SELECT 1 FROM TEMP_HM A WHERE A.HM = TEST_YMDHM.HM)
	AND YMD <= '20181030'
	AND YMD > '20180930';
	COMMIT;
	4. TBCA_DLY_INPTH_LDIN_STAT 일별_인입경로_인입_현황
		INSERT INTO TBCA_DLY_INPTH_LDIN_STAT
		(`DT`, 
		 INPTH_SECD,
		`CALL_CNT`, 
		`USER_CNT`, 
		`TR_CNT`, 
		`RLTM_INQIRE_CNT`, 
		`RLTM_TRANSFR_CNT`, 
		`PRCS_DT`
		)
		SELECT CONCAT('2019', RIGHT(YMD, 4))
			, '1'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CALL_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS USER_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '2') AS TR_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_INQIRE_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_TRANSFR_CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		WHERE YMD <= '20180917'
		AND YMD >= '20180101'
		GROUP BY YMD
		union all
		SELECT CONCAT('2019', RIGHT(YMD, 4))
			, '2'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CALL_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS USER_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '2') AS TR_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_INQIRE_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_TRANSFR_CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		WHERE YMD <= '20180917'
		AND YMD >= '20180101'
		GROUP BY YMD
		union all
		SELECT CONCAT('2019', RIGHT(YMD, 4))
			, '3'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CALL_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS USER_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '2') AS TR_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_INQIRE_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_TRANSFR_CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		WHERE YMD <= '20180917'
		AND YMD >= '20180101'
		GROUP BY YMD
		union all
		SELECT CONCAT('2019', RIGHT(YMD, 4))
			, '4'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CALL_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS USER_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '2') AS TR_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_INQIRE_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_TRANSFR_CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		WHERE YMD <= '20180917'
		AND YMD >= '20180101'
		GROUP BY YMD
		union all
		SELECT CONCAT('2019', RIGHT(YMD, 4))
			, '5'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CALL_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS USER_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '2') AS TR_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_INQIRE_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_TRANSFR_CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		WHERE YMD <= '20180917'
		AND YMD >= '20180101'
		GROUP BY YMD
		union all
		SELECT CONCAT('2019', RIGHT(YMD, 4))
			, '6'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CALL_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS USER_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '2') AS TR_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_INQIRE_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_TRANSFR_CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		WHERE YMD <= '20180917'
		AND YMD >= '20180101'
		GROUP BY YMD
		union all
		SELECT CONCAT('2019', RIGHT(YMD, 4))
			, '7'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CALL_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS USER_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '2') AS TR_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_INQIRE_CNT
			, LPAD(FLOOR(RAND()*1000000), 2, '1') AS RLTM_TRANSFR_CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		WHERE YMD <= '20180917'
		AND YMD >= '20180101'
		GROUP BY YMD;
		
		COMMIT;
		
5. TBCA_MNBY_MEDA_CALL_STAT

		INSERT INTO TBCA_MNBY_MEDA_CALL_STAT
		(`YM`, 
		 MEDA_SECD,
		`CNT`, 
		`PRCS_DT`
		)
		SELECT CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
			, '1'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		GROUP BY CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
		union all
		SELECT CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
			, '2'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		GROUP BY CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
		union all
		SELECT CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
			, '3'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		GROUP BY CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
		union all
		SELECT CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
			, '4'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		GROUP BY CONCAT('2019', LEFT(RIGHT(YMD, 4), 2));
		COMMIT;
		
6. TBCA_MNBY_IVR_USE_STAT		
		
		INSERT INTO TBCA_MNBY_IVR_USE_STAT
		(`YM`, 
		 IVR_USE_SECD,
		`CNT`, 
		`PRCS_DT`
		)
		SELECT CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
			, '1'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		GROUP BY CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
		union all
		SELECT CONCAT('2019', LEFT(RIGHT(YMD, 4), 2))
			, '2'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TEST_YMDHM
		GROUP BY CONCAT('2019', LEFT(RIGHT(YMD, 4), 2));
		COMMIT;
		
7. TBCA_MNBY_SRVC_END_STAT
		INSERT INTO TBCA_MNBY_SRVC_END_STAT
		(`YM`, 
		 END_RSN_SECD,
		`CNT`, 
		`PRCS_DT`
		)
		SELECT YM
			, '1'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '2'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '3'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '4'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '5'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM;
		COMMIT;
		
		
8. TBCA_MNBY_USER_CALL_STAT
		INSERT INTO TBCA_MNBY_USER_CALL_STAT
		(`YM`, 
		 USER_SECD,
		`CNT`, 
		`PRCS_DT`
		)
		SELECT YM
			, '1'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '2'
			, LPAD(FLOOR(RAND()*1000000), 4, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '3'
			, LPAD(FLOOR(RAND()*1000000), 2, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM;
		COMMIT;
		
9. TBCA_MNBY_AGE_CALL_STAT		
		INSERT INTO TBCA_MNBY_AGE_CALL_STAT
		(`YM`, 
		 AGRDE_SECD,
		`CNT`, 
		`PRCS_DT`
		)
		SELECT YM
			, '2'
			, LPAD(FLOOR(RAND()*1000000), 4, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '3'
			, LPAD(FLOOR(RAND()*1000000), 5, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '4'
			, LPAD(FLOOR(RAND()*1000000), 6, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '5'
			, LPAD(FLOOR(RAND()*1000000), 4, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '6'
			, LPAD(FLOOR(RAND()*1000000), 3, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM
		UNION ALL
		SELECT YM
			, '7'
			, LPAD(FLOOR(RAND()*1000000), 2, '7') AS CNT
			, CURRENT_TIMESTAMP
		FROM TBCA_MNBY_MEDA_CALL_STAT
		GROUP BY YM;
		COMMIT;
		
10. TBCA_5MLY_LDINCALL_STAT 5분별_인입콜_현황
		INSERT INTO TBCA_5MLY_LDINCALL_STAT(DT, HM, CNT, PRCS_DT)
		SELECT CONCAT('2019', RIGHT(YMD, 4)), HM, LPAD(FLOOR(RAND()*1000000), 6, '1') AS CNT, CURRENT_TIMESTAMP
		FROM TEST_YMDHM 
		WHERE EXISTS(SELECT 1 FROM TEMP_5HM A WHERE A.HM = TEST_YMDHM.HM)
		AND YMD <= '20181008'
		AND YMD > '20180930'
		AND HM <= '1800';
		COMMIT;		
		
11. TBCA_DLY_SRVC_STAT 일별_서비스_현황
		INSERT INTO TBCA_DLY_SRVC_STAT (DT, SRVC_CD, CNT, PRCS_DT)
		SELECT DT
			,TBCA_SRVC_CD.SRVC_CD
			,LPAD(FLOOR(RAND()*1000000), 4, '7')
			,CURRENT_TIMESTAMP
		FROM TBCA_DLY_LDIN_STAT
		INNER JOIN TBCA_SRVC_CD
		ON 1=1
		WHERE DT > '20190930';
		COMMIT;
		
12. TBCA_DLY_LAST_SRVC_STAT 일별_마지막_서비스_현황
		INSERT INTO TBCA_DLY_LAST_SRVC_STAT (DT, SRVC_CD, CNT, PRCS_DT)
		SELECT DT
			,TBCA_SRVC_CD.SRVC_CD
			,LPAD(FLOOR(RAND()*1000000), 4, '7')
			,CURRENT_TIMESTAMP
		FROM TBCA_DLY_LDIN_STAT
		INNER JOIN TBCA_SRVC_CD
		ON 1=1
		WHERE DT > '20190930';
		COMMIT;
		
13. TBCA_DLY_SRVC_END_STAT 일별_서비스_종료_현황
		INSERT INTO TBCA_DLY_SRVC_END_STAT (DT, SRVC_CD, END_RSN_SECD, CNT, PRCS_DT)
		SELECT DT
			,SRVC_CD
			,'0' as END_RSN_SECD
			,CNT/4
			,PRCS_DT
		FROM TBCA_DLY_SRVC_STAT
		WHERE DT > '20190930'
		union all 
		SELECT DT
			,SRVC_CD
			,'1' as END_RSN_SECD
			,CNT/3
			,PRCS_DT
		FROM TBCA_DLY_SRVC_STAT
		WHERE DT > '20190930'
		union all 
		SELECT DT
			,SRVC_CD
			,'2' as END_RSN_SECD
			,CNT - CNT/3 - CNT/4
			,PRCS_DT
		FROM TBCA_DLY_SRVC_STAT
		WHERE DT > '20190930';
		COMMIT;
		
14. TBCA_30MLY_SRVC_STAT   30분별_서비스_현황
		INSERT INTO TBCA_30MLY_SRVC_STAT (DT, HM, SRVC_CD, CNT, PRCS_DT)
		SELECT A.DT
			,B.HM
			,A.SRVC_CD
			,LPAD(FLOOR(RAND()*1000000), 4, '1') AS CNT
			,A.PRCS_DT
		FROM TBCA_DLY_SRVC_STAT A, TEMP_HM B
		WHERE DT > '20190930'
		ORDER BY A.DT, B.HM;
		COMMIT;		
		
15. TBCA_DLY_SRVC_USER_STAT		
		insert into TBCA_DLY_SRVC_USER_STAT
		select DT, SRVC_CD, '1', LPAD(FLOOR(RAND()*1000000), 5, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD
		union all
		select DT, SRVC_CD, '2', LPAD(FLOOR(RAND()*1000000), 4, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD
		union all
		select DT, SRVC_CD, '3', LPAD(FLOOR(RAND()*1000000), 2, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD;
		commit;
		
16. TBCA_DLY_SRVC_AGE_STAT		
		insert into TBCA_DLY_SRVC_AGE_STAT
		select DT, SRVC_CD, '2', LPAD(FLOOR(RAND()*1000000), 2, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD
		union all
		select DT, SRVC_CD, '3', LPAD(FLOOR(RAND()*1000000), 3, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD
		union all
		select DT, SRVC_CD, '4', LPAD(FLOOR(RAND()*1000000), 4, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD
		union all
		select DT, SRVC_CD, '5', LPAD(FLOOR(RAND()*1000000), 4, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD
		union all
		select DT, SRVC_CD, '6', LPAD(FLOOR(RAND()*1000000), 2, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD
		union all
		select DT, SRVC_CD, '7', LPAD(FLOOR(RAND()*1000000), 2, '1') AS CNT, current_timestamp
		from TBCA_CLDR, TBCA_SRVC_CD;
		commit;
		
		
17. TBCA_DLY_ERR_STAT (일별_에러_현황)
		INSERT INTO TBCA_DLY_ERR_STAT (DT, ERR_CD, CNT, PRCS_DT)
		SELECT DT
			,TBCA_ERR_CD.ERR_CD
			,LPAD(FLOOR(RAND()*1000000), 4, '7')
			,CURRENT_TIMESTAMP
		FROM TBCA_DLY_LDIN_STAT
		INNER JOIN TBCA_ERR_CD
		ON 1=1
		WHERE DT > '20190930';
		COMMIT;
		
18. TBCA_DLY_TR_ERR_STAT (일별_TR_에러_현황)
		INSERT INTO TBCA_DLY_TR_ERR_STAT (DT, TRAN_CD, ERR_CD, CNT, PRCS_DT)
		SELECT DT
			,TBCA_TR_CD.TR_CD
			,TBCA_ERR_CD.ERR_CD
			,LPAD(FLOOR(RAND()*1000000), 4, '7')
			,CURRENT_TIMESTAMP
		FROM TBCA_DLY_LDIN_STAT
		INNER JOIN TBCA_ERR_CD
		ON 1=1
		INNER JOIN TBCA_TR_CD
		ON 1=1;
		COMMIT;
		
		
19. TBCA_30MLY_ERR_STAT (30분별_에러_현황)
	INSERT INTO TBCA_30MLY_ERR_STAT(DT, HM, ERR_CD, CNT, PRCS_DT)
	SELECT CONCAT('2019', RIGHT(YMD, 4)), HM, TBCA_ERR_CD.ERR_CD, LPAD(FLOOR(RAND()*1000000), 6, '1') AS CNT, CURRENT_TIMESTAMP
	FROM TEST_YMDHM 
	INNER JOIN TBCA_ERR_CD
	ON 1=1
	WHERE EXISTS(SELECT 1 FROM TEMP_HM A WHERE A.HM = TEST_YMDHM.HM)
	AND YMD <= '20180930'
	AND YMD > '20180917'
	AND HM <= '1500';
	COMMIT;
	
20 TBCA_5MLY_LDIN_STAT 5분별_인입_현황	
		INSERT INTO TBCA_5MLY_LDIN_STAT(DT, HM, CALL_CNT, TR_CNT, ERR_CNT, PRCS_DT)
		SELECT CONCAT('2019', RIGHT(YMD, 4)), HM, LPAD(FLOOR(RAND()*1000000), 6, '1') AS CALL_CNT, LPAD(FLOOR(RAND()*1000000), 7, '6') AS TR_CNT, LPAD(FLOOR(RAND()*1000000), 2, '1') AS ERR_CNT, CURRENT_TIMESTAMP
		FROM TEST_YMDHM 
		WHERE EXISTS(SELECT 1 FROM TEMP_5HM A WHERE A.HM = TEST_YMDHM.HM)
		AND YMD <= '20181030'
		AND YMD > '20181008';
		COMMIT;		
		
21. TBCA_HLY_TR_STAT 시간별_TR_현황		
	INSERT INTO TBCA_HLY_TR_STAT(DT, TM, CNT, PRCS_DT)
	SELECT
		A.DT, A.TM, LPAD(FLOOR(RAND()*1000000), 2, '1') AS CNT, CURRENT_TIMESTAMP
	FROM (
		SELECT CONCAT('2019', RIGHT(YMD, 4)) AS DT, LEFT(HM, 2) AS TM
		FROM TEST_YMDHM 
		WHERE EXISTS(SELECT 1 FROM TEMP_HM A WHERE A.HM = TEST_YMDHM.HM)
		AND YMD <= '20181030'
		AND YMD > '20180501'
	) A
	GROUP BY A.DT, A.TM;
	COMMIT;
	

22. TBCA_DLY_CALL_KND_STAT 일별_콜_종류_현황
		INSERT INTO TBCA_DLY_CALL_KND_STAT
		(`DT`, 
		 CALL_KND_SECD,
		`CALL_CNT`, 
		`USER_CNT`, 
		`PRCS_DT`
		)
		SELECT DT
			, CASE WHEN INPTH_SECD = '4' THEN 0 ELSE INPTH_SECD END
			, CALL_CNT
			, USER_CNT
			, PRCS_DT
		FROM TBCA_DLY_INPTH_LDIN_STAT
		WHERE INPTH_SECD < '5';
		COMMIT;
		
23. TBCA_DLY_MEDA_CALL_STAT
	INSERT INTO TBCA_DLY_MEDA_CALL_STAT
	(DT, MEDA_SECD, CNT, PRCS_DT)
	SELECT
		DT
		,'1'
		,LPAD(FLOOR(RAND()*1000000), 5, '1') AS CNT
		,CURRENT_TIMESTAMP
	FROM TBCA_DLY_LDIN_STAT
	UNION ALL
	SELECT
		DT
		,'2'
		,LPAD(FLOOR(RAND()*1000000), 5, '1') AS CNT
		,CURRENT_TIMESTAMP
	FROM TBCA_DLY_LDIN_STAT
	UNION ALL
	SELECT
		DT
		,'3'
		,LPAD(FLOOR(RAND()*1000000), 5, '1') AS CNT
		,CURRENT_TIMESTAMP
	FROM TBCA_DLY_LDIN_STAT
	UNION ALL
	SELECT
		DT
		,'4'
		,LPAD(FLOOR(RAND()*1000000), 5, '1') AS CNT
		,CURRENT_TIMESTAMP
	FROM TBCA_DLY_LDIN_STAT;
	COMMIT;