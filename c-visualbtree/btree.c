
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "util/inp.h"

struct Node
{
  int value;
  struct Node *left;
  struct Node *right;
};

void tdelete2(int data)
{
  if(level == NULL)
  {
    puts("트리가 존재하지 않습니다.");
    return;
  }
  struct Node *pp = tinsert_base(data, level0, true);
  if()
}


struct Node *tsearch_base(int data, struct Node *main, bool getp)
{
  // getp가 true이면 대상 노드의 부모를 리턴합니다.
  struct Node *p = main;
  struct Node *pp = NULL;
  while(p != NULL)
  {
    if(data < p->value)
    {
      if(p->left == NULL) return (getp)?pp:p;
      else if(p->left->value == 0) return (getp)?pp:p;
      pp = p;
      p = p->left;
    }
    else if(data > p->value)
    {
      if(p->right == NULL) return (getp)?pp:p;
      else if(p->right->value == 0) return (getp)?pp:p;
      pp = p;
      p = p->right;
    }
    else return (getp)?pp:p;
  }
  return p;
}

struct Node *tsearch_max(struct Node *main, bool getp)
{
  // getp가 true이면 대상 노드의 부모를 리턴합니다.
  struct Node *p = main;
  while(p != NULL)
  {
    if(getp)
    {
      if(p->right->right != NULL) p = p->right;
      else break;
    }
    else
    {
      if(p->right != NULL) p = p->right;
      else break;
    }
  }
  return p;
}

struct Node *alloc_node(int data)
{
  struct Node* p = (struct Node*)malloc(sizeof(struct Node));
  memset(&(*p), 0, sizeof(struct Node)); // 윈도우에선 동적할당한 주소의 값들을 초기화하지 않기 때문에 초기화
  p->value = data;
  return p;
  //return malloc(sizeof(struct Node));
}

struct Node *level0;

struct Node *tsearch(int data)
{
  struct Node *p = level0;
  while(p != NULL)
  {
    if(data < p->value)
    {
      if(p->left == NULL) return p;
      else if(p->left->value == 0) return p;
      else p = p->left;
    }
    else if(data > p->value)
    {
      if(p->right == NULL) return p;
      else if(p->right->value == 0) return p;
      else p = p->right;
    }
    else return p;
  }
  return p;
}

void tinsert(int data)
{
  struct Node* p;
  struct Node* np;
  if(level0 == NULL) level0 = alloc_node(data);
  else if((p=tsearch(data))->value == data) return;//printf("%d는(은) 이미 트리에 보관되어 있습니다.\n", data);
  else
  {
    np = alloc_node(data);
    if(p->value > data) p->left = np;
    else if(p->value < data) p->right = np;
  }
}

void tdelete(int data)
{
  struct Node *p, *pp, *cp, *cpp;
  char leftright=0;
  pp = level0;
  if(pp == NULL)
  {
    puts("트리가 존재하지 않습니다.");
    return;
  }
  p = pp;
  while(p != NULL)
  {
    if(p->value == data) break; // 찾았다
    else if(p->value > data)
    {
      pp = p;
      p = p->left;
      leftright = 0;
    }
    else if(p->value < data)
    {
      pp = p;
      p = p->right;
      leftright = 1;
    }
  }
  if(p == NULL)
  {
    printf("%d는(은) 트리에 존재하지 않습니다.\n", data);
    return;
  }
  else
  {
    if(p->left == NULL && p->right == NULL) // 단말노드
    {
      if(p == level0) level0 = NULL; // 기원 노드임
      else if(leftright == 0) pp->left = NULL;
      else if(leftright == 1) pp->right = NULL;
      free(p);
    }
    else if(p->left == NULL || p->right == NULL) // degree 1
    {
      if(p->left != NULL)
      {
        if(p == level0) level0 = level0->left;
        else if(leftright == 0) pp->left = p->left;
        else if(leftright == 1) pp->right = p->left;
      }
      else if(p->right != NULL)
      {
        if(p == level0) level0 = level0->right;
        else if(leftright == 0) pp->left = p->right;
        else if(leftright == 1) pp->right = p->right;
      }
      free(p);
    }
    else // degree 2
    {
      cpp = p;
      cp = cpp->left;
      while(cp->right != NULL)
      {
        cpp = cp;
        cp = cp->right;
      }
      if(cpp == p)
      {
        // 위 반복문을 한 번도 실행하지 않았음. 단말노드가 아니지만 삭제 대상의 왼쪽 노드는 오른쪽 노드를 가지지 않을 때.
        // 25를 삭제한다고 가정했을 때 25보다 작은 수들 중에서 가장 큰 수가 바로 왼쪽 노드일 때.
        p->left = cp->left;
        p->value = cp->value;
      }
      else if(cp->left == NULL && cp->right == NULL)
      {
        // 삭제 대상의 왼쪽 노드가 단말노드일 때
        cpp->right = NULL;
        p->value = cp->value;
      }
      else
      {
        // 삭제 대상의 왼쪽의 자식 노드들 중에서 가장 큰 값을 가진 노드가 왼쪽 자식 노드를 가지고 있을 때
        // 미싱링크를 방지해야 함
        cpp->right = cp->left;
        p->value = cp->value;
      }
      free(cp);
    }
  }
}

void inorder(struct Node *wt)
{
  if(wt == NULL) return;
  else
  {
    inorder(wt->left);
    printf("%d ", wt->value);
    inorder(wt->right);
  }
}

int screen[64][32];

void pick(int x, int y, int data)
{
  if(x<32 && y<64) screen[y][x] = data;
}

void drawline(int x, int y, int line)
{
  if(x<32 && y<64) screen[y][x] = line;
}

int print(struct Node *wt, int x, int y)
{
  if(wt==NULL) return 0;
  pick(x, y, wt->value);
  /*
  if(wt->left != NULL) x = print(wt->left, x, y+1);
  if(wt->right != NULL)x = print(wt->right, x+1, y+1);
  */
  int ox = x;
  if(wt->left != NULL)
  {
    drawline(x, y+1, 999);
    x = print(wt->left, x, y+2);
  }
  if(wt->right != NULL)
  {
    for(int i=ox+1; i<=x+1; i++)
      drawline(i, y, 998);
    drawline(x+1, y+1, 999);
    x = print(wt->right, x+1, y+2);
  }
  return x;
}

void cleanup()
{
  memset(&screen, 0, sizeof(int)*64*32);
}

void show()
{
  cleanup();
  print(level0, 0, 0);
  int x=0, y=0;
  char lc = 0;
  for(y=0; y<64; y++)
  {
    for(x=0; x<32; x++)
    {
      if(screen[y][x] == 998) printf("###");
      else if(screen[y][x] == 999) printf("  #");
      else if(screen[y][x] !=0 )printf("%3d", screen[y][x]);
      else
      {
        printf("   ");
        lc++;
      }
    }
    puts("");
    if(lc == 32)
      break;
    lc=0;
  }
}

void help()
{
  puts("명령어 리스트");
  puts("help\t\t\t명령어 리스트 출력");
  puts("set <숫자>\t\t정수를 트리에 보관");
  puts("del <숫자>\t\t정수를 트리에서 삭제");
  puts("search <숫자>\t\t요청한 정수가 있는지 검사");
  puts("print\t\t\t일렬 출력");
  puts("show\t\t\t트리 출력");
  puts("exit\t\t\t프로그램 종료");
}

void main()
{
  help();
  do
  {
    printf("명령줄 >");

    inp_set();
    if(strcmp(inp_get(0), "set") == 0)
    {
      for(int i=1; i<inp_getmax(); i++)
      {
        int data = atoi(inp_get(i));
        if(level0 != NULL)
          if(tsearch(data)->value == data)
            printf("%2d 번째 입력(%3d) 이미 트리에 보관중\n", i, data);
        if(data>0 && data<100)
          tinsert(data);
        else
          printf("%2d 번째 입력(%3d) 허용 범위를 벗어남(1~99)\n", i, data);
      }

      printf("inorder: ");
      inorder(level0);
      puts("");

    }
    else if(strcmp(inp_get(0), "exit") == 0)
    {
      break;
    }
    else if(strcmp(inp_get(0), "help") == 0)
    {
      help();
    }
    else if(strcmp(inp_get(0), "print") == 0)
    {
      printf("inorder: ");
      inorder(level0);
      puts("");
    }
    else if(strcmp(inp_get(0), "show") == 0)
    {
      puts("show: ");
      show();
      puts("");
    }
    else if(strcmp(inp_get(0), "search") == 0)
    {
      int data = atoi(inp_get(1));
      if(data>0 && data<100)
      {
        if(tsearch(data) == NULL) puts("트리가 없습니다.");
        else
        {
          if(tsearch(data)->value == data) puts("있습니다.");
          else puts("없습니다.");
        }
      }
      else
      {
        puts("1~99 사이의 숫자만 입력하세요.");
      }

    }
    else if(strcmp(inp_get(0), "del") == 0)
    {
      int data = atoi(inp_get(1));
      if(data>0 && data<100)
      {
        tdelete(data);
      }
      else
      {
        puts("1~99 사이의 숫자만 입력하세요.");
      }
    }
    else if(strcmp(inp_get(0), "") == 0);
    else
    {
      puts("없는 명령입니다.");
    }

  }while(1);
  return;

}
