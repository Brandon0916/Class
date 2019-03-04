# Pong V3

import pygame, sys, time
from pygame.locals import *

# User-defined classes

# User-defined functions

def main():

   # Initialize pygame
   pygame.init()
   
   #allow for keys to be held
   pygame.key.set_repeat(20, 20)

   # Set window size and title, and frame delay
   surfaceSize = (500, 400) # example window size
   windowTitle = 'Pong V1' # example title
   frameDelay = 0.01 # smaller is faster game

   # Create the window
   surface = pygame.display.set_mode(surfaceSize, 0, 0)
   pygame.display.set_caption(windowTitle)

   # create and initialize objects
   gameOver = False
   
   #Ball
   centerBall = [250, 200]
   colorBall = pygame.Color('orange')
   radiusBall = 5
   speedBall = [4,1]
   
   #Player1
   color1 = pygame.Color('orange')
   player1 = pygame.Rect(60, 150, 15, 80)
   
   #Player2
   color2 = pygame.Color('orange')
   player2 = pygame.Rect(425, 150, 15, 80)
   
   #Font
   scores = [0,0]
   
   size = surface.get_size()

   # Draw objects
   pygame.draw.circle(surface, colorBall, centerBall, radiusBall, 0)
   pygame.draw.rect(surface, color1, player1)
   pygame.draw.rect(surface,color2,player2)
   # Refresh the display
   pygame.display.update()

   # Loop forever
   while True:
      # Handle events
      for event in pygame.event.get():
         if event.type == QUIT:
            pygame.quit()
            sys.exit()
         # Handle additional events
         #If a key is held down, do this
         if event.type == KEYDOWN:
            #Moves paddle down only if the size-player1[3] >= size
            #Should this be put into a user defined function?
            keys = pygame.key.get_pressed()
            if keys[K_q] and player1[1] >= 0:
               player1[1] = player1[1] - 15
            if keys[K_a] and player1[1] <= size[1]-player1[3]:
               player1[1] = player1[1] + 15
               
            #Player 2
            if keys[K_p] and player2[1] >=0:
               player2[1] = player2[1] - 15
            if keys[K_l] and player2[1] <= size[1]-player2[3]:
               player2[1] = player2[1] + 15
               
               
               
      # Update and draw objects for the next frame
      gameOver = update(surface, colorBall, centerBall, radiusBall, speedBall, color1, color2, player1, player2,scores)

      # Refresh the display
      pygame.display.update()

      # Set the frame speed by pausing between frames
      time.sleep(frameDelay)

def displayScore(scores, surface):
   size = surface.get_size()   
   fontSize = 40
   scoreFont = pygame.font.SysFont(None,fontSize, True)
   scoreDisplay1 = scoreFont.render(str(scores[0]),True,pygame.Color('orange'))
   scoreDisplay2 = scoreFont.render(str(scores[1]),True,pygame.Color('orange'))
   surface.blit(scoreDisplay1,(0,0))
   surface.blit(scoreDisplay2,(size[0]-fontSize,0))

def moveBall(surface, center, radius, speed, player1, player2, scores):
   #Gets the size to check if the dot touches an edge
      size = surface.get_size()

      #Check Collision for score
     
      # if the x value of the ball passes the radius line, add a point
      # There are exceptions for if the ball goes past the window border
      # just in case the speed is so great it doesn't exactly = the border
      if center[0] >= size[0]-radius:
         scores[0] = scores[0] + 1
         # ditto, except other side   
      if center[0] <= radius:
            scores[1] = scores[1] + 1
      
      #Move the ball, check collisions
      for coord in range(0,2):        
         center[coord] = center[coord] + speed[coord]
         # Ball bounces off left or top border
         if center[coord] < radius:
            speed[coord] = -speed[coord]
         # Ball bounces off right or bottom border
         if center[coord] + radius > size[coord]:
            speed[coord] = -speed[coord]
                 
         # Ball bounces off front of paddle but continues through back side
         if player1.collidepoint(center) == True and speed[0] < 0:
            speed[coord] = -speed[coord]
         if player2.collidepoint(center) == True and speed[0] > 0:
            speed[coord] = -speed[coord]
            
         
            



def update(surface, colorBall, centerBall, radiusBall, speedBall, color1, color2, player1, player2, scores):
   # Check if the game is over. If so, end the game and
   # returnTrue. Otherwise, update the game objects, draw
   # them, and return False.

   if scores[0] == 11 or scores[1] == 11: # check if the game is over
      return True
   else: # continue the game
      #Clear the screen
      surface.fill(pygame.Color('black'))
      
      #Move ball and add score
      moveBall(surface,centerBall, radiusBall, speedBall, player1, player2,scores)
      pygame.draw.circle(surface, colorBall, centerBall, radiusBall, 0)
      
      #Draw Rect
      pygame.draw.rect(surface, color1, player1)
      pygame.draw.rect(surface, color2, player2)
      
      #Draw score
      # Contain these commands within a new function, then call the function
      # Then they will add as they are supposed to
      displayScore(scores,surface)
      return False

main()