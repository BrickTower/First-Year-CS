"""
Ethan Gregory
egregor4@uwo.ca
251433485
Creation date: 11-08-2024
Defines all the necessary functions for main to function, including cleaning the text, generating necessary dictionaries,
analyzing the words to determine a common emotion, and finally assembling them together to write its report.
"""

EMOTIONS = ['anger', 'joy', 'fear', 'trust', 'sadness', 'anticipation']


def clean_text(comment): # Cleans string
    cleaned_string = "" # Init. Variable

    for letter in comment.lower(): # If the letter is not a-z, change it to a space
        if not letter.isalpha():
            cleaned_string += " "
        else:
            cleaned_string += letter

    return cleaned_string # Return

def make_keyword_dict(keyword_file_name): # Make a dictionary with all keywords
    keyword_dict = {} # Init. Keyword Dict.
    keyword_file = open(str(keyword_file_name), "r")


    line = keyword_file.readline()
    while line != "": # Loop through TSV file
        clean_line = line.strip() # Clean trailing
        keyword = clean_line.split("\t") # Split into list
        word = keyword[0] # Assign String to variable
        emotion_dict = {} # Init. Emotion Dict.


        for i in range(1, len(EMOTIONS) + 1): # Loop through each of the motions
            emotion_dict[EMOTIONS[i - 1]] = int(keyword[i]) # Assign each word's emotion value to said word
        keyword_dict[word] = emotion_dict # Assign emotion values to the word
        line = keyword_file.readline() # NExt line

    keyword_file.close() # CLose file
    return keyword_dict


def classify_comment_emotion(comment, keywords):
    cleaned_comment = clean_text(comment.lower()).split(" ")
    keyword_dict = keywords # Run make keyword_dict
    feeling_score = {} # Init. empty dict.

    for i in EMOTIONS: # Pre-fill the dictionary with the feelings
        feeling_score[i] = 0

    for word in cleaned_comment: # For each word, if it's value is positive, add the value into the final score

        if word in keyword_dict:
            for value in keyword_dict[word]:
                if keyword_dict[word][value] > 0:
                    feeling_score[value] = feeling_score[value] + keyword_dict[word][value]

    highest_score = -1 # Init. var
    most_common_emote = ""
    for i in feeling_score: # Find most common emotion
        if feeling_score[i] > highest_score: # If word's score is higher than before, make it the new highest value
            highest_score = feeling_score[i]
            most_common_emote = i

    return most_common_emote # Returns the most common emotion

def make_comments_list(filter_country, comments_file_name):
    HEADERS = ['comment_id', 'username', 'country', 'text'] # DEFINE DESCRIPTORS

    comments_file = open(comments_file_name, "r") # Open File
    comment_list = [] # Open list
    info_dict = {} #Open a blank Dictionary

    line = comments_file.readline()
    while line != "": # Loop through file
        clean_line = line.strip() # Clean whitespace & split into a list
        information = clean_line.split(",")

        info_dict = { # Define info_dict
            'comment_id'.strip(): int(information[0].strip()),
            'username'.strip(): information[1].strip(),
            'country'.strip(): information[2].strip(),
            'text'.strip(): information[3].strip()
        }

        if info_dict["country"] == filter_country.lower() or filter_country.lower() == 'all':
            comment_list.append(info_dict) # Apply copy to list if it matches input country / all

        line = comments_file.readline() # Move to next line
    comments_file.close()

    return comment_list # Return comment list

def make_report(comment_list, keywords, report_filename):
    if len(comment_list) == 0: # If no comments, raise runtime error
        raise RuntimeError("No comments in dataset!")

    report_file = open(report_filename, "w") # Open file for writing
    emotion_count = {} # Init. empty dict. to store most common emotion in comment

    # Fill emotion dict. without hardcoding
    for i in range(0, len(EMOTIONS)):
        emotion_count[EMOTIONS[i]] = 0

    for current_comment in comment_list:
        common_emotion = classify_comment_emotion(current_comment["text"], keywords)
        emotion_count[common_emotion] += 1
        #for emotion in emotion_count:
        #    if common_emotion == emotion:
        #        emotion_count[emotion] += 1
        #        break

    # Get the most common emotion
    highest_score = -1
    most_common_emotion = "anger" # Init. fallback
    for i in emotion_count:
        if emotion_count[i] > highest_score:
            highest_score = emotion_count[i]
            most_common_emotion = i

    # Write the most common emotion into the dataset
    report_file.write("Most common emotion: {}\n\n".format(most_common_emotion))

    # Give percentage counts for each emotion
    # First, add everything together
    total_comments = len(comment_list)

    report_file.write("Emotion Totals\n") # Write header
    for one_emotion in emotion_count:
        # Convert each emotion amount to a percentage
        emote_percent = (emotion_count[one_emotion] / total_comments) * 100

        # Write into file
        report_file.write("{}: {} ({:.2f}%)\n".format(one_emotion, emotion_count[one_emotion], emote_percent))

    report_file.close() # Close file

    return most_common_emotion # Return the most common emotion

