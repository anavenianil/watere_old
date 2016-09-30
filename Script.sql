update leave_account set status=0 where eleven is not null and creation_date>to_date('01-Jan-2012','DD-Mon-YYYY');
update leave_account set status=0 where thirtyfive is not null and creation_date>to_date('01-Jan-2012','DD-Mon-YYYY');
update request_workflow_history set request_stage=3,stage_status=102 where id in (select id from request_workflow_history where assigned_to='SF0015' and request_stage=1 and status=2 and request_type_id=2);
update request_workflow_history set request_stage=3,stage_status=102 where id in (select id from request_workflow_history where assigned_to='SF0008' and status=2 and request_type_id=2);
update request_workflow_history set request_stage=3,stage_status=102 where id in (select id from request_workflow_history where assigned_to='SF0014' and status=2 and request_type_id=2);
update request_workflow_history set request_stage=3,stage_status=102 where id in (select id from request_workflow_history where assigned_to='SF0256' and status=2 and request_type_id=2);
update request_workflow_history set request_stage=3,stage_status=102 where id in (select id from request_workflow_history where assigned_to='SF0426' and status=2 and request_type_id=2);
update request_workflow_history set request_stage=3,stage_status=102 where id in (select id from request_workflow_history where assigned_to='SF0013' and status=2 and request_type_id=2);
