<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<p>Hi ${prescription.patientName}</p>
<p>Diagonosis ${prescription.diagnosis}</p>

<table>
    <#list prescription.medicineList as med>
    <tr>
        <#if med.name?has_content>
            <td>${med.name}</td>
        <#else>
           <td></td>
        </#if>
        <#if med.type?has_content>
            <td>${med.type}</td>
        <#else>
            <td></td>
        </#if>

        <#if med.dosage?has_content>
            <td>${med.dosage}</td>
        <#else>
            <td></td>
        </#if>

        <#if med.duration?has_content>
            <td>${med.duration}</td>
        <#else>
            <td></td>
        </#if>

        <#if med.frequency?has_content>
            <td>${med.frequency}</td>
        <#else>
            <td></td>
        </#if>

        <#if med.remarks?has_content>
            <td>${med.remarks}</td>
        <#else>
            <td></td>
        </#if>
    </tr>
    </#list>
</table>


<p>Regards,</p>
<p>
    <em>${prescription.firstName} ${prescription.lastName} at BookMyConsultation</em> <br />
</p>
</body>
</html>