//
//  GooglePlusLoginPlugin.m
//  AsthmaApp
//
//  Created by tessy on 01/08/14.
//
//

#import "GoogleConnectPlugin.h"
#import <GoogleOpenSource/GoogleOpenSource.h>
#import "AppDelegate.h"


@interface GoogleConnectPlugin ()
{
    CDVInvokedUrlCommand *callbackCmd;
}
@end

@implementation GoogleConnectPlugin

- (void) cordovaGooglePlusLogin:(CDVInvokedUrlCommand *)command {
    callbackCmd=command;
    
    //Google Plus Methods
    GPPSignIn *signIn = [GPPSignIn sharedInstance];
    signIn.shouldFetchGooglePlusUser = YES;
    //signIn.shouldFetchGoogleUserEmail = YES;  // Uncomment to get the user's email
    
    // You previously set kClientId in the "Initialize the Google+ client" step
    signIn.clientID =[[NSBundle mainBundle] objectForInfoDictionaryKey:@"GPlusClientID"];
    
    //NSString *appVersion =[[NSBundle mainBundle] objectForInfoDictionaryKey:@"GPlusClientID";
    
    // Uncomment one of these two statements for the scope you chose in the previous step
    signIn.scopes = @[ kGTLAuthScopePlusLogin ];  // "https://www.googleapis.com/auth/plus.login" scope
    //signIn.scopes = @[ @"profile" ];            // "profile" scope
    
    // Optional: declare signIn.actions, see "app activities"
    signIn.delegate = self;
    [signIn authenticate];
}

- (void) cordovaGooglePlusLogout:(CDVInvokedUrlCommand *)command {
    callbackCmd=command;
    
 [[GPPSignIn sharedInstance] signOut];
 [[GPPSignIn sharedInstance] disconnect];
 CDVPluginResult *pluginResult = [ CDVPluginResult resultWithStatus    : CDVCommandStatus_OK];
     [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackCmd.callbackId];
 
}
#pragma mark - Social Media Callback Methods
//Google Plus callback method

-(void)finishedWithAuth:(GTMOAuth2Authentication *)auth error:(NSError *)error {

    GPPSignIn *signIn = [GPPSignIn sharedInstance];

    if (signIn.authentication) {
        NSLog(@"Login Status: Authenticated");
        //NSLog(@"Name:%@, ProfilePic:%@, Email:%@ About me:%@, UserID:%@",person.displayName,person.image.url,[GPPSignIn sharedInstance].authentication.userEmail,person.aboutMe,person.identifier);
        [[[GPPSignIn sharedInstance] plusService] executeQuery:[GTLQueryPlus queryForPeopleGetWithUserId:@"me"] completionHandler:^(GTLServiceTicket *ticket, GTLPlusPerson *person, NSError *error)
         {
             if(error)
             {
                 NSString *message = [NSString stringWithFormat:@"Google Plus Error: %@",error.localizedDescription];
                 //[self performSelector:@selector(showAlert:) onThread:[NSThread mainThread] withObject:message waitUntilDone:YES];
                 UIAlertView *alert = [[UIAlertView alloc]initWithTitle:@"Warning" message:message delegate:nil cancelButtonTitle:nil otherButtonTitles:@"OK", nil];
                 [alert show];
             }
             else
             {
                 NSLog(@"Data fetched");
                 NSDictionary *personDetails=[self getProfileInformation:person];
                 NSLog(@"User:%@",personDetails.JSONString);
                 // Create an instance of CDVPluginResult, with an OK status code.
                 // Set the return message as the Dictionary object (jsonObj)...
                 // ... to be serialized as JSON in the browser
                 CDVPluginResult *pluginResult = [ CDVPluginResult resultWithStatus    : CDVCommandStatus_OK messageAsDictionary : personDetails];
                 // Execute sendPluginResult on this plugin's commandDelegate, passing in the ...
                 // ... instance of CDVPluginResult
                 [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackCmd.callbackId];
             }
             
         }];


        
       
        
    } else {
        // To authenticate, use Google+ sign-in button.
        
        NSLog(@"Login Status: Not Authenticated");
        // ... to be serialized as JSON in the browser
        CDVPluginResult *pluginResult =[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"Error in Login"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackCmd.callbackId];
        [[GPPSignIn sharedInstance] authenticate];
    }
    
    
}
#pragma - Util Methods
-(NSDictionary *) getProfileInformation:(GTLPlusPerson *)person{
    NSLog(@"User Name:%@",person.displayName);
    NSLog(@"User Email:%@",[GPPSignIn sharedInstance].userEmail);
    NSLog(@"User Gender:%@",person.gender);
    NSMutableDictionary *personDict=[NSMutableDictionary dictionary];
    
    [personDict setValue:person.displayName forKey:@"name"];
    [personDict setValue:[GPPSignIn sharedInstance].userEmail forKey:@"emails"];
    [personDict setValue:person.gender forKey:@"gender"];
    
    return personDict;

}

@end
