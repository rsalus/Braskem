# Braskem

## Tools

I created a number of simple tools at Braskem to help with productivity and temporarily fill in for missing functionality for some systems. These include...

**Invite Exporter**, a program which grabs some data fields from a .html document and creates a .csv file out of them. For my use case, these data fields was visitor information (name, time due, etc). This was for the Envoy visitor management system, because at the time they did not offer the ability to export an invite log into a spreadsheet (despite offering that functionality for the visitor log). As Braskem's office was located in a commercial office building, they needed to upload this list to the building's C-Cure system.

**CSV Convert**, a program similar to Invite Exporter which converts a txt file to a csv file after grabbing some data fields (name, email, phone) in a certain format. This was used to convert our employee database of ~500 employees into Envoy's system.

## PRIME & MS Flow/SharePoint

PRIME is Braskem's Approval process for change orders to their plant additives. Originally it was hosted entirely on SharePoint, using SharePoint's forms, list, and WorkFlow functionality. The system was found to be clunky and over-complex with long loading times and hard-to-access data. During my time at Braskem I designed, configured, and implemented a revamped system using MS Forms as a front-end, SharePoint as a data depository, and MS Flow as the back-end actor. My internship ended shortly after the launch of the first Pilot using this new system. Included are the three exported zip files of the Flows I designed: Form Fetcher, Approval Request, and Request Re-Trigger.

**PRIME FRONT-END PROCESS FLOW (Credit: Samantha Garvey)**

[Diagram](https://raw.githubusercontent.com/rsalus/Braskem/master/frontend.png)

**BREAKDOWN OF PRIME FLOW BACK-END**

Three Flow processes running:
* Form Fetcher
* Approval Request
* Request Re-Trigger

**Form Fetcher** monitors the PRIME form for responses. Upon a response, it sanitizes some inputs from the responses & creates a SharePoint list item with the response information.

**Approval Request** monitors changes in the SharePoint list. Upon creation of a new list item or any modification to an existing one, Approval Request will generate an Approval from the Pending Approval By column in the fetched list item. It will then generate & send an email to the fetched Approver and wait for an update to the Approval created. Upon receiving an update, Approval Request will move the Approver to either the Approved By or Rejected By column in the SharePoint list item and inform the original approval Requestor of the outcome of their Approval. 

**Request Re-Trigger** is a flow that is designed to re-trigger Approval Request by modifying the previous SharePoint list item with previously saved information. This is accomplished by monitoring for file creation in /Documents/DUMMY CACHE on the SharePoint page, which Approval Request creates at the end of its process. 

To sum, Form Fetcher grabs the information and creates a SharePoint list item. Approval Request creates Approvals, sends out the needed emails, and updates the SharePoint list item as required. Request Re-Trigger forces Approval Request to loop through each Approver in the Pending Approval By column.
